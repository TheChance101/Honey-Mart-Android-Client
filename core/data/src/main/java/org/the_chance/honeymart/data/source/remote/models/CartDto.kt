package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class CartDto(
    @SerializedName("products")
    val products: List<CartProductDto>,
    @SerializedName("total")
    val total: Double? = null,
)

data class CartProductDto(
    @SerializedName("id")
    val productId: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("imageUrl")
    val imageUrl: String? = null,
)