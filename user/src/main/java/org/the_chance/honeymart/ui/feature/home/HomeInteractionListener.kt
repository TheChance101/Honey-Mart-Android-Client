package org.the_chance.honeymart.ui.feature.home

interface HomeInteractionListener {
    fun getData()

    fun getAllDoneOrders()

    fun getAllDiscoverProducts()

    fun onClickPagerItem(marketId: Long)

    fun onClickCouponClipped(couponId: Long)

    fun onClickProductItem(productId: Long)

    fun onClickFavoriteNewProduct(productId: Long)
    fun onClickFavoriteDiscoverProduct(productId: Long)
    fun onClickSearchBar()
}