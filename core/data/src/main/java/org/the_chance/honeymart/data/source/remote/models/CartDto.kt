package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class CartDto (

    @SerializedName("productId")
    val productId: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("total")
    val total: Double? = null,
    )