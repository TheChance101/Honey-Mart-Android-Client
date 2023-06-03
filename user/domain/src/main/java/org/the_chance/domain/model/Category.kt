package org.the_chance.domain.model

import org.the_chance.data.source.remote.models.CategoryDto

data class Category(
    val id: Int,
    val name: String,
    val imageId: Int
)

fun CategoryDto.asCategory() = Category(
    id = categoryId.toInt(),
    name = categoryName,
    imageId = imageId
)