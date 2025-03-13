package com.healyks.app.data.repo

import android.util.Log
import coil.network.HttpException
import com.google.firebase.auth.FirebaseAuth
import com.healyks.app.data.model.CustomResponse
import com.healyks.app.data.model.UserDetails
import com.healyks.app.data.remote.UserApi
import com.healyks.app.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.net.UnknownHostException
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val userApi: UserApi,
    private val auth: FirebaseAuth
) {

    suspend fun getIdToken(): String {
        return try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val idTokenResult = currentUser.getIdToken(true).await()
                val token = idTokenResult?.token ?: "No Token Found"
                Log.d("AuthRepo", token)
                "Bearer $token"
            } else {
                "No Token Found"
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error getting token", e)
            "No Token Found"
        }
    }

    suspend fun verifyToken(
        accessToken: String
    ): Flow<UiState<CustomResponse<Unit>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response = userApi.verifyToken(accessToken)
                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to verify token"))
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

    suspend fun getUserDetails(accessToken: String): Flow<UiState<CustomResponse<UserDetails>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response = userApi.getUserDetails(accessToken)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to get user data"))
                }
            } catch (e: UnknownHostException) {
                emit(UiState.Failed("No Internet Connection!"))
            } catch (e: HttpException) {
                emit(UiState.Failed("Server error. Please try again later"))
            } catch (e: Exception) {
                emit(UiState.Failed(e.message ?: "Something went wrong"))
            }
        }
    }

    suspend fun postUserBody (
        accessToken: String,
        postUserBody: UserDetails
    ): Flow<UiState<CustomResponse<Unit>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response: CustomResponse<Unit> =
                    userApi.postUserBody(accessToken, postUserBody)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to post user details"))
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