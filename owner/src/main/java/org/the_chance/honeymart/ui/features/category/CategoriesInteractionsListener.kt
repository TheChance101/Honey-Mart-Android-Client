package org.the_chance.honeymart.ui.features.category

/**
 * Created by Aziza Helmy on 8/7/2023.
 */
interface CategoriesInteractionsListener {

    fun onClickCategory(categoryId: Long)
    fun onNewCategoryNameChanged(categoryName: String)
    fun onClickAddCategory(name: String, categoryIconID: Int)
    fun resetShowState(visibility: Visibility)
    fun resetSnackBarState()
    fun onClickNewCategoryIcon(categoryIconId: Int)
    fun updateCategory(category: CategoriesUiState)
    fun deleteCategory(id: Long)
    fun getAllCategory()
}