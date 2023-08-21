package org.the_chance.honeymart.ui.feature.home

interface HomeInteractionListener {
    fun getData()

    fun onClickPagerItem(marketId: Long)

    fun onClickGetCoupon(couponId: Long)

    fun onClickProductItem(productId: Long)

    fun onClickFavoriteNewProduct(productId: Long)

    fun onClickFavoriteDiscoverProduct(productId: Long)

    fun onClickSearchBar()

    fun onClickCategory(categoryId: Long, position: Int)

    fun onClickChipCategory(marketId: Long)


}