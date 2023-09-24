package org.the_chance.honeymart.ui.feature.search

interface SearchInteraction {
    fun onClickFilter()
    fun onClickRandomSearch()
    fun onClickAscendingSearch()
    fun onClickDescendingSearch()
    fun onClickProduct(productId: Long)
    fun onclickTryAgain()
    fun onChangeProductScrollPosition(position: Int)
}