package org.the_chance.honeymart.ui.feature.category

interface MarketInteractionListener {
    fun onGetData()
    fun onClickCategory(categoryId: Long, position: Int)
}