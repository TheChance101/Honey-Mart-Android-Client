package org.the_chance.honeymart.ui.feature.new_products

interface RecentProductListener {
    fun onClickProductItem(productId: Long)
    fun getRecentProducts()
    fun onClickFavoriteNewProduct(productId: Long)
    fun onClickDiscoverButton()
}