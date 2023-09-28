package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AdminLoginDto(
    @SerializedName("tokens")
    val tokens: TokensDto?,
    @SerializedName("name")
    val name: String?,
)
