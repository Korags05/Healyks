package com.healyks.app.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healyks.app.data.model.CustomResponse
import com.healyks.app.data.model.GeminiBody
import com.healyks.app.data.model.GeminiResponse
import com.healyks.app.data.repo.AnalyzeRepo
import com.healyks.app.data.repo.UserRepo
import com.healyks.app.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyzeViewModel @Inject constructor(
    private val analyzeRepo: AnalyzeRepo,
    private val userRepo: UserRepo
): ViewModel() {

    private val _postSymptom: MutableStateFlow<UiState<CustomResponse<GeminiResponse>>> =
        MutableStateFlow(UiState.Idle)
    val postSymptom = _postSymptom.asStateFlow()

    fun postSymptom(geminiBody: GeminiBody) {
        _postSymptom.value = UiState.Loading
        viewModelScope.launch {
            try {
                analyzeRepo.analyzeSymptom(userRepo.getIdToken(), geminiBody).collect { response->
                    Log.d("AnalyzeViewModel", "API Response: $response")
                    _postSymptom.value = response
                    if (response is UiState.Success) {
                        Log.d("AnalyzeViewModel", "Symptom analysis successful: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.d("AnalyzeViewModel", "postSymptom: ${e.message}")
                _postSymptom.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun resetPostSymptomState() {
        _postSymptom.value = UiState.Idle
    }
}