package com.healyks.app.data.util

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.healyks.app.CHANNEL
import com.healyks.app.R
import com.healyks.app.data.model.Reminder
import com.healyks.app.data.use_cases.UpdateUseCase
import com.healyks.app.di.ReminderReceiverEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.runBlocking

const val DONE = "DONE"
const val REJECT = "REJECT"

class ReminderReceiver : BroadcastReceiver() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onReceive(context: Context, intent: Intent) {

        // Get the updateUseCase from EntryPoint instead of direct injection
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            ReminderReceiverEntryPoint::class.java
        )
        val updateUseCase = entryPoint.updateUseCase()

        val reminderJson = intent.getStringExtra(REMINDER)
        if (reminderJson == null) {
            return
        }

        val reminder = Gson().fromJson(reminderJson, Reminder::class.java)

        when(intent.action) {
            DONE -> {
                // Mark as taken and cancel notification
                handleDoneAction(context, reminder, updateUseCase)

                // Cancel notification
                NotificationManagerCompat.from(context).cancel(reminder.id.hashCode())

                if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    mediaPlayer.release()
                }
            }
            REJECT -> {
                // Just dismiss the notification without marking as taken
                // Stop any alarms if they're active
                cancelAlarm(context, reminder)

                // Cancel notification
                NotificationManagerCompat.from(context).cancel(reminder.id.hashCode())

                // Stop media player if it's playing
                if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    mediaPlayer.release()
                }
            }
            else -> {
                // Show notification with the reminder
                mediaPlayer = MediaPlayer.create(context, R.raw.alarm_music)
                showNotification(context, reminder)

                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.release()
                }
                mediaPlayer.start()
            }
        }
    }

    private fun handleDoneAction(context: Context, reminder: Reminder, updateUseCase: UpdateUseCase) {
        // Cancel the alarm
        cancelAlarm(context, reminder)

        // Update database to mark as taken
        runBlocking {
            updateUseCase.invoke(
                reminder.copy(
                    isTaken = true,
                    isRepeat = false
                )
            )
        }

        // Stop media player if it's playing
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    private fun showNotification(
        context: Context,
        reminder: Reminder
    ) {
        val doneIntent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(REMINDER, Gson().toJson(reminder))
            action = DONE
        }
        val donePendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id.hashCode(),
            doneIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val closeIntent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(REMINDER, Gson().toJson(reminder))
            action = REJECT
        }
        val closePendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id.hashCode() + 1,  // Different request code for second action
            closeIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL)
            .setSmallIcon(R.drawable.splashlogo)
            .setContentTitle("Medication Reminder")
            .setContentText(reminder.name.plus(" ${reminder.dosage}"))
            .addAction(R.drawable.ic_check, "Done", donePendingIntent)
            .addAction(R.drawable.ic_close, "Close", closePendingIntent)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(context)
                    .notify(reminder.id.hashCode(), notification)
            }
        } else {
            NotificationManagerCompat.from(context)
                .notify(reminder.id.hashCode(), notification)
        }
    }
}