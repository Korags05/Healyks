package com.healyks.app.data.remote

import com.healyks.app.data.model.CustomResponse
import com.healyks.app.data.model.GeminiBody
import com.healyks.app.data.model.GeminiResponse
import com.healyks.app.data.util.Constants
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AnalyzeApi {
    @POST(Constants.ANALYZE_ENDPOINT)
    suspend fun analyzeSymptom(
        @Header("Authorization") accessToken: String,
        @Body geminiBody: GeminiBody
    ): CustomResponse<GeminiResponse>
}