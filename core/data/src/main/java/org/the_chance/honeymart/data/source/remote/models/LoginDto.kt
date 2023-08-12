package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("accessToken")
    val accessToken: String? = null,
    @SerializedName("refreshToken")
    val refreshToken: String? = null,
)
