package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class OwnerProfileDto(
    @SerializedName("ownerId")
    val ownerId: Long?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("email")
    val email: String?,
)
