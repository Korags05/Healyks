package com.healyks.app.data.local.reminder

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.healyks.app.data.model.Reminder

@Database(
    entities = [Reminder::class],
    version = 1,
    exportSchema = false  // Temporary fix for schema warning
)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao  // Rename for consistency

    companion object {
        @Volatile
        private var INSTANCE: ReminderDatabase? = null

        fun getInstance(context: Context): ReminderDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReminderDatabase::class.java,
                    "reminder_database"  // Better database name
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}