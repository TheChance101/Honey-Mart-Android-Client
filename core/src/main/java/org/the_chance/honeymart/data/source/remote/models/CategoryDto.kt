package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("categoryId")
    val categoryId: Long? = null,
    @SerializedName("imageId")
    val imageId: Int? = null,
    @SerializedName("categoryName")
    val categoryName: String? = null
)

