package org.the_chance.honeymart.ui.addCategory

import org.the_chance.honeymart.domain.model.CategoryEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.products.CategoryUiState

data class AddCategoryUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val nameCategory: String = "",
    val error: ErrorHandler? = null,
    val position: Int = 0,
    val categoryImageId: Int = 0,
    val positionCategory: Int = 0,
    val categoryId: Long = 0L,
    val categories: List<CategoryUIState> = emptyList(),
    val categoryImages: List<CategoryImageUIState> = emptyList()
)

data class CategoryImageUIState(
    val categoryImageId: Int = 0,
    val image: Int = 0,
    val isSelected: Boolean = false,
)

data class CategoryUIState(
    val categoryId: Long = 0L,
    val categoryIcon: Int = 0,
    val categoryName: String = ""
)

fun ListCategoryImages.toCategoryImageUIState() =
    CategoryImageUIState(
        categoryImageId = this.categoryImageId,
        image = this.categoryImage
    )
fun CategoryEntity.toCategoryUIState() =
    CategoryUIState(
        categoryIcon = this.categoryImageId,
        categoryName = this.categoryName,
        categoryId = this.categoryId
    )