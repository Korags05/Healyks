package com.healyks.app.data.use_cases

import com.healyks.app.data.repo.ReminderRepository
import javax.inject.Inject

class GetAllReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {

    operator fun invoke() = reminderRepository.getAllReminders()

}