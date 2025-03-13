package com.healyks.app.data.model

import com.google.gson.annotations.SerializedName

data class Lifestyle(
    @SerializedName("alcohol")
    val alcohol: Boolean,

    @SerializedName("smoking")
    val smoking: Boolean,

    @SerializedName("physicalActivity")
    val physicalActivity: String? = null
)