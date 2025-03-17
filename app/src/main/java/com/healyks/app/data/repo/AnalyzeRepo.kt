package com.healyks.app.data.repo

import coil.network.HttpException
import com.healyks.app.data.model.CustomResponse
import com.healyks.app.data.model.GeminiBody
import com.healyks.app.data.model.GeminiResponse
import com.healyks.app.data.remote.AnalyzeApi
import com.healyks.app.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class AnalyzeRepo @Inject constructor(
    private val analyzeApi: AnalyzeApi
) {
    suspend fun analyzeSymptom(
        accessToken : String,
        geminiBody: GeminiBody
    ): Flow<UiState<CustomResponse<GeminiResponse>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response: CustomResponse<GeminiResponse> =
                    analyzeApi.analyzeSymptom(accessToken, geminiBody)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to post symptoms"))
                }
            } catch (e: UnknownHostException) {
                emit(UiState.Failed("No Internet Connection!"))
            } catch (e: HttpException) {
                emit(UiState.Failed("Server error. Please try again later."))
            } catch (e: Exception) {
                emit(UiState.Failed(e.message ?: "Something went wrong."))
            }
        }
    }
}