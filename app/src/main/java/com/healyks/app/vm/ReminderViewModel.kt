package com.healyks.app.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healyks.app.data.model.Reminder
import com.healyks.app.data.use_cases.DeleteUseCase
import com.healyks.app.data.use_cases.GetAllReminderUseCase
import com.healyks.app.data.use_cases.InsertUseCase
import com.healyks.app.data.use_cases.UpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val insertUseCase: InsertUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val updateUseCase: UpdateUseCase,
    private val getAllReminderUseCase: GetAllReminderUseCase
) : ViewModel() {

    // Use WhileSubscribed for better lifecycle management
    val uiState = getAllReminderUseCase.invoke().map { UiState(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UiState()
        )

    suspend fun insert(reminder: Reminder): Long =
        insertUseCase.invoke(reminder)

    fun update(reminder: Reminder) = viewModelScope.launch {
        updateUseCase.invoke(reminder)
    }

    fun delete(reminder: Reminder) = viewModelScope.launch {
        deleteUseCase.invoke(reminder)
    }
}

data class UiState(
    val data: List<Reminder> = emptyList()
)