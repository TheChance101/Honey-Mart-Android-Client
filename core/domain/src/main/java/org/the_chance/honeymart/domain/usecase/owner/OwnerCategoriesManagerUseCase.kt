package org.the_chance.honeymart.domain.usecase.owner

import org.the_chance.honeymart.domain.usecase.AddToCategoryUseCase
import org.the_chance.honeymart.domain.usecase.DeleteCategoryUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCategoriesInMarketUseCase
import org.the_chance.honeymart.domain.usecase.UpdateCategoryUseCase
import javax.inject.Inject

data class OwnerCategoriesManagerUseCase @Inject constructor(
    val getAllCategories: GetAllCategoriesInMarketUseCase,
    val addCategoryUseCase: AddToCategoryUseCase,
    val updateCategoryUseCase: UpdateCategoryUseCase,
    val deleteCategoryUseCase: DeleteCategoryUseCase,
)
