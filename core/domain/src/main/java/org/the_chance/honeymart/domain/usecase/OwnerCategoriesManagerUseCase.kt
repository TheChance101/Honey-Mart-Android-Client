package org.the_chance.honeymart.domain.usecase

import javax.inject.Inject

data class OwnerCategoriesManagerUseCase @Inject constructor(
    val getAllCategories: GetAllCategoriesInMarketUseCase,
    val addCategoryUseCase: AddToCategoryUseCase,
    val updateCategoryUseCase: UpdateCategoryUseCase,
    val deleteCategoryUseCase: DeleteCategoryUseCase,
)
