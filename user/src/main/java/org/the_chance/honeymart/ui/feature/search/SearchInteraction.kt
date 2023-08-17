package org.the_chance.honeymart.ui.feature.search

interface SearchInteraction {
    fun onClickFilter()
    fun getAllRandomSearch()
    fun getAllAscendingSearch()
    fun getAllDescendingSearch()
    fun onClickProduct(productId: Long)
}