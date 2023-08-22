package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class RequestDto(
    @SerializedName("marketId")
    val marketId: Int? = null,
    @SerializedName("marketName")
    val marketName: String? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("owner")
    val owner: OwnerProfileDto? = null
)