package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class MarketInfoDto(
    @SerializedName("marketId")
    val marketId: Long? = null,
    @SerializedName("marketName")
    val marketName: String? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("marketStatus")
    val marketStatus: Boolean? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("address")
    val address: String? = null
)