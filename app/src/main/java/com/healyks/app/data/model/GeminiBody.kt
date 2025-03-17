package com.healyks.app.data.model

import com.google.gson.annotations.SerializedName

data class GeminiBody(
    @SerializedName("symptoms")
    val symptoms: String
)