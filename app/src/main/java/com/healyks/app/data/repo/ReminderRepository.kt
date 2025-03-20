package com.healyks.app.data.repo

import com.healyks.app.data.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

    suspend fun insert(reminder: Reminder): Long

    suspend fun update(reminder: Reminder)

    suspend fun delete(reminder: Reminder)

    fun getAllReminders() : Flow<List<Reminder>>

}