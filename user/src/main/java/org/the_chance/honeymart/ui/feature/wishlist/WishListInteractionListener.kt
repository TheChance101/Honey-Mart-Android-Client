package org.the_chance.honeymart.ui.feature.wishlist


interface WishListInteractionListener {
    fun onClickProduct(productId: Long)
    fun onClickFavoriteIcon(productId: Long)
    fun getWishListProducts()
    fun onClickDiscoverButton()
}