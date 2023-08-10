package org.the_chance.honeymart.ui.addCategory

interface AddCategoryInteractionListener {
    fun changeNameCategory(nameCategory: String)
    fun onClickAddCategory()
    fun onClickCategoryImage(categoryImageId: Int)
    fun onClickCategory(categoryId: Long)
    fun resetSnackBarState()
}