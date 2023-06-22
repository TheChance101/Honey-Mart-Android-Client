package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class WishListDto(
    @SerializedName("id")
    val productId: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("images")
    val images: List<String>? = null
)