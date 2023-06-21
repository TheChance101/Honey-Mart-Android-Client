package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class CartDto(
    @SerializedName("products")
    val products: List<CartProductsDto>? = null,
    @SerializedName("total")
    val total: Double? = null
)

data class CartProductsDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("count")
    val count: Long? = null,
    @SerializedName("price")
    val price: Double? = null,
)