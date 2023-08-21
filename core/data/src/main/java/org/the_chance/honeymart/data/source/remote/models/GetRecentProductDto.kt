package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class GetRecentProductDto  (
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("images")
    val images:List<String>? = null
)