package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("price")
    val price: Double
)