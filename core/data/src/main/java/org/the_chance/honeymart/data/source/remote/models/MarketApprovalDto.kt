package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class MarketApprovalDto(
    @SerializedName("isMarketApproved")
    val isMarketApproved: Boolean? = null,
    @SerializedName("marketId")
    val marketId: Long? = null
)
