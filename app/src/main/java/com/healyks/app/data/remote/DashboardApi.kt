package com.healyks.app.data.remote

import com.healyks.app.data.model.CustomResponse
import com.healyks.app.data.model.DashboardResponse
import com.healyks.app.data.util.Constants
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DashboardApi {
    @GET(Constants.DASHBOARDBYID_ENDPOINT)
    suspend fun getDashboardDataById(
        @Header("Authorization") accessToken: String,
        @Path("id") dashboardID : String
    ): CustomResponse<DashboardResponse>

    @GET(Constants.DASHBOARD_ENDPOINT)
    suspend fun getAllDashboardData(
        @Header("Authorization") accessToken: String
    ): CustomResponse<List<DashboardResponse>>
}