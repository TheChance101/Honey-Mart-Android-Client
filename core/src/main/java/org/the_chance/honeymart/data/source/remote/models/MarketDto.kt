package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

   data class MarketDto(
    @SerializedName("id")
    val marketId: Long? = null,
    @SerializedName("name")
    val marketName: String? = null
)