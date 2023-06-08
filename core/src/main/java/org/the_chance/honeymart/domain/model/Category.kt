package org.the_chance.honeymart.domain.model

import org.the_chance.honeymart.data.source.remote.models.CategoryDto

data class Category(
    val id: Long,
    val name: String,
    val imageId: Int
)

 fun CategoryDto.asCategory() = Category(
    id = categoryId,
    name = categoryName,
    imageId = imageId
)