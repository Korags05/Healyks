package com.healyks.app.data.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("age")
    val age: Int,

    @SerializedName("allergies")
    val allergies: List<String>,

    @SerializedName("bloodGroup")
    val bloodGroup: String,

    @SerializedName("chronicDiseases")
    val chronicDiseases: List<String>,

    @SerializedName("email")
    val email: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("lifestyle")
    val lifestyle: Lifestyle,

    @SerializedName("medications")
    val medications: List<String>,

    @SerializedName("weight")
    val weight: Int
)