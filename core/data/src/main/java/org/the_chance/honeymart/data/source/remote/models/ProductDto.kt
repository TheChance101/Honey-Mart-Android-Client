package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("quantity")
    val quantity: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("images")
    val images: List<String>? = null
)