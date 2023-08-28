package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.domain.model.Category

/**
 * Created by Aziza Helmy on 6/12/2023.
 */
internal fun CategoryDto.toCategory() = Category(
    categoryId = categoryId ?: 0L,
    categoryName = categoryName ?: "",
    categoryImageId = imageId ?: 0,
)