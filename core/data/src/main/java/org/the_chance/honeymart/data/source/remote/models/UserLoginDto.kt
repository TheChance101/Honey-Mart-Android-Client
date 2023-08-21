package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

class UserLoginDto(
    @SerializedName("refreshToken")
    val refreshToken: String? = null,
    @SerializedName("accessToken")
    val accessToken: String? = null,
)