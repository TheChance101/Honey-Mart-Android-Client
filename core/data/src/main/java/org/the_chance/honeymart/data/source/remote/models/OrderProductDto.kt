package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class OrderProductDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("count")
    val count: Double? = null,
    @SerializedName("price")
    val price: Double? = null
)
