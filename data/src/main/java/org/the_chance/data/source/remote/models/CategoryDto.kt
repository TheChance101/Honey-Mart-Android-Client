package org.the_chance.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("categoryId")
    val categoryId: Long,
    @SerializedName("imageId")
    val imageId:Int,
    @SerializedName("categoryName")
    val categoryName: String
)

