package com.healyks.app.data.remote

import com.healyks.app.data.model.CustomResponse
import com.healyks.app.data.model.UserDetails
import com.healyks.app.data.util.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {
    @GET(Constants.VERIFY_TOKEN_ENDPOINT)
    suspend fun verifyToken(
        @Header("Authorization") accessToken: String
    ): CustomResponse<Unit>

    @GET(Constants.USERDETAILS_ENDPOINT)
    suspend fun getUserDetails(
        @Header("Authorization") accessToken: String
    ): CustomResponse<UserDetails>

    @POST(Constants.POSTUSERBODY_ENDPOINT)
    suspend fun postUserBody(
        @Header("Authorization") accessToken: String,
        @Body postUserBody: UserDetails
    ): CustomResponse<Unit>
}