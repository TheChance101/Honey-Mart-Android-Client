package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.CategoryDto
import org.the_chance.honeymart.domain.model.CategoryEntity

/**
 * Created by Aziza Helmy on 6/12/2023.
 */
internal fun CategoryDto.toCategoryEntity() = CategoryEntity(
    categoryId = categoryId,
    categoryName = categoryName,
    categoryImageId = imageId
)