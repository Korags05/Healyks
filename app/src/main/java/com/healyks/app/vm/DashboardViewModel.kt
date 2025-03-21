package com.healyks.app.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healyks.app.data.model.CustomResponse
import com.healyks.app.data.model.DashboardResponse
import com.healyks.app.data.repo.DashboardRepo
import com.healyks.app.data.repo.UserRepo
import com.healyks.app.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepo: DashboardRepo,
    private val userRepo: UserRepo
): ViewModel() {

    private val _dashboardDataById: MutableStateFlow<UiState<CustomResponse<DashboardResponse>>> =
        MutableStateFlow(UiState.Idle)
    val dashboardDataByID = _dashboardDataById.asStateFlow()

    fun getDashboardDataById(dashboardId: String) {
        _dashboardDataById.value = UiState.Loading
        viewModelScope.launch {
            try {
                dashboardRepo.getDashboardDataById(userRepo.getIdToken(),dashboardId).collect { response ->
                    _dashboardDataById.value = response
                    Log.d("Dashboard Data By Id", "$response")
                }
            }catch (e: IOException) {
                _dashboardDataById.value = UiState.Failed("Network error: ${e.message ?: "No internet connection or network error"}")
            } catch (e: HttpException) {
                _dashboardDataById.value = UiState.Failed("HTTP error: ${e.message ?: "Internal Server Error"}")
            } catch (e: Exception) {
                _dashboardDataById.value = UiState.Failed("Unexpected error: ${e.message ?: "Unable to Fetch Event By Id"}")
            }
        }
    }

    fun resetGetDashboardDataById() {
        _dashboardDataById.value = UiState.Idle
    }

    private val _allDashboardData: MutableStateFlow<UiState<CustomResponse<List<DashboardResponse>>>> =
        MutableStateFlow(UiState.Idle)
    val allDashboardData = _allDashboardData.asStateFlow()

    fun getAllDashboardData() {
        _allDashboardData.value = UiState.Loading
        viewModelScope.launch {
            try {
                dashboardRepo.getAllDashboardData(userRepo.getIdToken()).collect { response ->
                    _allDashboardData.value = response
                    Log.d("All Dashboard Data", "$response")
                }
            } catch (e: IOException) {
                _allDashboardData.value = UiState.Failed("Network error: ${e.message ?: "No internet connection or network error"}")
            } catch (e: HttpException) {
                _allDashboardData.value = UiState.Failed("HTTP error: ${e.message ?: "Internal Server Error"}")
            } catch (e: Exception) {
                _allDashboardData.value = UiState.Failed("Unexpected error: ${e.message ?: "Unable to Fetch Event By Id"}")
            }
        }
    }

    fun resetGetAllDashboardData() {
        _allDashboardData.value = UiState.Idle
    }

}