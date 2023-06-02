package data.remote.models

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("categoryId")
    val categoryId: Long,
    @SerializedName("imageId")
    val imageId:Int,
    @SerializedName("categoryName")
    val categoryName: String
)

data class MarketWithCategories(
    @SerializedName("marketId")
    val marketId: Long,
    @SerializedName("marketName")
    val marketName: String,
    @SerializedName("categories")
    val categories: List<CategoryDto>
)
