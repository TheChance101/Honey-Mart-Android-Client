package org.the_chance.honeymart.domain.model

import org.the_chance.honeymart.data.source.remote.models.CategoryDto

data class Category(
    val categoryId: Long?,
    val categoryName: String?,
    val categoryImageId: Int?
)

fun CategoryDto.asCategory() = Category(
    categoryId = categoryId,
    categoryName = categoryName,
    categoryImageId = imageId
)