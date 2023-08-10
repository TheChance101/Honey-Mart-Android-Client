package org.the_chance.honeymart.ui.addCategory

interface AddCategoryListener {
    fun changeNameCategory(nameCategory: String)
    fun onClickAddCategory()
    fun onClickCategoryImage(categoryImageId: Int)
    fun onClickCategory(categoryId: Long)
}