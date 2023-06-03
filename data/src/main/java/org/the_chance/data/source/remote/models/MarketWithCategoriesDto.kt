package org.the_chance.data.source.remote.models

import com.google.gson.annotations.SerializedName
import org.the_chance.data.source.remote.models.CategoryDto

data class MarketWithCategoriesDto(
        @SerializedName("marketId")
        val marketId: Long,
        @SerializedName("marketName")
        val marketName: String,
        @SerializedName("categories")
        val categories: List<CategoryDto>
    )

