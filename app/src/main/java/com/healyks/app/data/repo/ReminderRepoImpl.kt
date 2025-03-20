package com.healyks.app.data.repo

import com.healyks.app.data.local.reminder.ReminderDao
import com.healyks.app.data.model.Reminder
import kotlinx.coroutines.flow.Flow

class ReminderRepoImpl(
    private val reminderDao: ReminderDao
) : ReminderRepository {


    override suspend fun insert(reminder: Reminder): Long {
        return reminderDao.insert(reminder)
    }

    override suspend fun update(reminder: Reminder) {
        reminderDao.update(reminder)
    }

    override suspend fun delete(reminder: Reminder) {
        reminderDao.delete(reminder)
    }

    override fun getAllReminders(): Flow<List<Reminder>> =
        reminderDao.getAllReminders()
}