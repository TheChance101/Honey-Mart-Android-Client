package org.the_chance.honeymart.ui.feature.wishlist

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class WishListUiEffect : BaseUiEffect {
    data class ClickProductEffect(val productId: Long) : WishListUiEffect()
    object  UnAuthorizedUserEffect : WishListUiEffect()
    object AddProductToWishList :WishListUiEffect()
    object ClickDiscoverEffect : WishListUiEffect()
    object DeleteProductFromWishListEffect : WishListUiEffect()
}
