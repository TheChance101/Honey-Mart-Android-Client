package org.the_chance.honeymart.ui.feature.category

interface CategoryInteractionListener {
    fun onGetData()
    fun onClickCategory(categoryId: Long, position: Int)
}