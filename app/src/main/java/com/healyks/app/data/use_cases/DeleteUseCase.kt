package com.healyks.app.data.use_cases

import com.healyks.app.data.model.Reminder
import com.healyks.app.data.repo.ReminderRepository
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {

    suspend operator fun invoke(reminder: Reminder) = reminderRepository.delete(reminder)

}