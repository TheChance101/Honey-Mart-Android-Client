package org.the_chance.honeymart.ui.features.category

import org.the_chance.honeymart.ui.features.update_category.UpdateCategoryUiState

/**
 * Created by Aziza Helmy on 8/7/2023.
 */
interface CategoriesInteractionsListener {

    fun onClickCategory(categoryId: Long)

    fun changeNameCategory(nameCategory: String)
    fun onClickAddCategory()
    fun onClickCategoryImage(categoryImageId: Int)
    fun updateStateToShowAddCategory(state: Boolean)
    fun resetSnackBarState()
    fun onUpdatedCategoryNameChanged(name: String)
    fun onClickCategoryIcon(categoryIconId: Int)
    fun updateCategory(category: UpdateCategoryUiState)
    fun onClickCancelUpdateCategory()
}