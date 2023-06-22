package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

   data class MarketDto(
    @SerializedName("marketId")
    val marketId: Long? = null,
    @SerializedName("marketName")
    val marketName: String? = null,
    @SerializedName("imageUrl")
    val imageUrl:String? = null
)