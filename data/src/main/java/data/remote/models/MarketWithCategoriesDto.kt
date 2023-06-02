package data.remote.models

import com.google.gson.annotations.SerializedName

data class MarketWithCategoriesDto(
        @SerializedName("marketId")
        val marketId: Long,
        @SerializedName("marketName")
        val marketName: String,
        @SerializedName("categories")
        val categories: List<CategoryDto>
    )

