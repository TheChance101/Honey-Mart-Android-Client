package data.remote.models

import com.google.gson.annotations.SerializedName

data class MarketDto(
    @SerializedName("marketId")
    val marketId: Long,
    @SerializedName("marketName")
    val marketName: String
)