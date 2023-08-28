package org.the_chance.honeymart.data.source.remote.models


import com.google.gson.annotations.SerializedName

data class ProfileUserDto(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("fullName")
    val fullName: String? = null,
    @SerializedName("profileImage")
    val profileImage: String? = null,
    @SerializedName("userId")
    val userId: Long? = null
)