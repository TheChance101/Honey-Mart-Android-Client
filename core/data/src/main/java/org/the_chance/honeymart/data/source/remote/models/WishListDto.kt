package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class WishListDto (
    @SerializedName("productId")
    val productId: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: String? = null,
)