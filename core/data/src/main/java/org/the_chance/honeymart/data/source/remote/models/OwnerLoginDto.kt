package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerLoginDto(
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("marketId")
    val marketId: Long?,
    @SerializedName("tokens")
    val tokens: TokensDto
)