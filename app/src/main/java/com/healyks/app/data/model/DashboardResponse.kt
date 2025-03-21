package com.healyks.app.data.model

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("content")
    val content: String,

    @SerializedName("crouselImg")
    val crouselImg: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("title")
    val title: String
)