package com.healyks.app.data.model

import com.google.gson.annotations.SerializedName

data class CustomResponse<T> (
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: T?
)