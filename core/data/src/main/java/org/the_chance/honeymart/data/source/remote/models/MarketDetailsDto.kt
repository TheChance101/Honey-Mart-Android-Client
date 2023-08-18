package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class MarketDetailsDto(
    @SerializedName("marketId")
    val marketId: Long? = null,
    @SerializedName("marketName")
    val marketName: String? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
    @SerializedName("productsCount")
    val productsCount: Int? = null,
    @SerializedName("categoriesCount")
    val categoriesCount: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("latitude")
    val latitude: Long? = null,
    @SerializedName("longitude")
    val longitude: Long? = null,
    @SerializedName("categories")
    val categories: List<CategoryDto>? = null,
)
