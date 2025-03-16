package com.healyks.app.vm

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.healyks.app.data.model.CustomResponse
import com.healyks.app.data.model.UserDetails
import com.healyks.app.data.remote.UserApi
import com.healyks.app.data.repo.UserRepo
import com.healyks.app.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _verifyTokenState: MutableStateFlow<UiState<CustomResponse<Unit>>> =
        MutableStateFlow(UiState.Idle)
    val verifyTokenState = _verifyTokenState.asStateFlow()

    // Update the ViewModel's resetVerifyTokenState function
    fun resetVerifyTokenState() {
        _verifyTokenState.value = UiState.Idle
    }

    fun verifyToken(accessToken: String) {
        _verifyTokenState.value = UiState.Loading
        viewModelScope.launch {
            try {
                userRepo.verifyToken("Bearer $accessToken")
                    .collect { response ->
                        _verifyTokenState.value = response
                        if (response is UiState.Success) {
                            Log.d("UserViewModel", "Token verified: ${response.data}")
                        }
                    }
            } catch (e: Exception) {
                Log.d("UserViewModel", "TokenVerification: ${e.message}")
                _verifyTokenState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    private val _postUserState: MutableStateFlow<UiState<CustomResponse<Unit>>> =
        MutableStateFlow(UiState.Idle)
    val postUserState = _postUserState.asStateFlow()

    fun postUser(postUserBody : UserDetails) {
        _postUserState.value = UiState.Loading
        viewModelScope.launch {
            try {
                userRepo.postUserBody(userRepo.getIdToken(), postUserBody).collect { response->
                    Log.d("UserViewModel", "API Response: $response") // Add this line
                    _postUserState.value = response
                    if (response is UiState.Success) {
                        Log.d("UserViewModel", "User details posted successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.d("UserViewModel", "postUser: ${e.message}")
                _postUserState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun resetPostUserState() {
        _postUserState.value = UiState.Idle
    }

    private val _getUserState: MutableStateFlow<UiState<CustomResponse<UserDetails>>> =
        MutableStateFlow(UiState.Idle)
    val getUserState = _getUserState.asStateFlow()

    fun getUser() {
        _getUserState.value = UiState.Loading
        viewModelScope.launch {
            try {
                userRepo.getUserDetails(userRepo.getIdToken()).collect { response->
                    _getUserState.value = response
                    if (response is UiState.Success) {
                        Log.d("UserViewModel", "User Details fetched successfully: ${response.data}")
                    }
                }
            } catch (e: Exception) {
                Log.d("UserViewModel", "getUserDetails: ${e.message}")
                _getUserState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun resetGetUserState() {
        _getUserState.value = UiState.Idle
    }

    // State to track if user details are filled
    private val _isUserDetailsFilled = MutableStateFlow(getUserDetailsFilledFromStorage())
    val isUserDetailsFilled: StateFlow<Boolean> get() = _isUserDetailsFilled

    // Function to set user details filled status
    fun setUserDetailsFilled(isFilled: Boolean) {
        viewModelScope.launch {
            _isUserDetailsFilled.value = isFilled
            saveUserDetailsFilledToStorage(isFilled)
        }
    }

    // Save the state to SharedPreferences
    private fun saveUserDetailsFilledToStorage(isFilled: Boolean) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            val key = "isUserDetailsFilled_${user.uid}" // Use UID as part of the key
            sharedPreferences.edit().putBoolean(key, isFilled).apply()
        }
    }

    // Retrieve the state from SharedPreferences
    private fun getUserDetailsFilledFromStorage(): Boolean {
        val user = firebaseAuth.currentUser
        return if (user != null) {
            val key = "isUserDetailsFilled_${user.uid}" // Use UID as part of the key
            sharedPreferences.getBoolean(key, false)
        } else {
            false
        }
    }

    // Reset the state when the user logs out or switches accounts
    fun resetUserDetailsFilled() {
        _isUserDetailsFilled.value = false
    }
}