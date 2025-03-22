package com.healyks.app.data.model

import com.google.gson.annotations.SerializedName

data class GeminiResponse(
    @SerializedName("condition")
    val condition: String,

    @SerializedName("recommendation")
    val recommendation: String,

    @SerializedName("homeRemedies")
    val homeRemedies: String
)