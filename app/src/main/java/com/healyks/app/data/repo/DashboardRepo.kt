package com.healyks.app.data.repo

import com.healyks.app.data.model.CustomResponse
import com.healyks.app.data.model.DashboardResponse
import com.healyks.app.data.remote.DashboardApi
import com.healyks.app.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class DashboardRepo @Inject constructor(
    private val dashboardApi: DashboardApi
) {
    suspend fun getDashboardDataById(
        accessToken: String,
        dashboardId: String
    ): Flow<UiState<CustomResponse<DashboardResponse>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response = dashboardApi.getDashboardDataById(accessToken, dashboardId)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to get dashboard data"))
                }
            } catch (e: IOException) {
                emit(UiState.Failed("No Internet Connection!"))
            } catch (e: HttpException) {
                emit(UiState.Failed("Server error. Please try again later"))
            } catch (e: Exception) {
                emit(UiState.Failed(e.message ?: "Something went wrong"))
            }
        }
    }

    suspend fun getAllDashboardData(
        accessToken: String
    ): Flow<UiState<CustomResponse<List<DashboardResponse>>>> {
        return flow {
            try {
                emit(UiState.Loading)
                val response = dashboardApi.getAllDashboardData(accessToken)
                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to get dashboards"))
                }
            } catch (e: IOException) {
                emit(UiState.Failed("No Internet Connection!"))
            } catch (e: HttpException) {
                emit(UiState.Failed("Server error. Please try again later"))
            } catch (e: Exception) {
                emit(UiState.Failed(e.message ?: "Something went wrong"))
            }
        }
    }
}