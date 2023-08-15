package org.the_chance.honeymart.ui.features.update_category

interface UpdateCategoryInteractionListener {

    fun onUpdatedCategoryNameChanged(name: String)
    fun onClickCategoryIcon(categoryIconId: Int)
    fun updateCategory(category: UpdateCategoryUiState)
    fun onClickCancelUpdateCategory()
}