package org.the_chance.honeymart.ui.feature.marketInfo

interface MarketInteractionListener {
    fun onGetData()
    fun onClickCategory(categoryId: Long, position: Int)
}