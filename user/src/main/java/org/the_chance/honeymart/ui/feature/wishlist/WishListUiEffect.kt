package org.the_chance.honeymart.ui.feature.wishlist

sealed class WishListUiEffect {
    data class ClickProductEffect(val productId: Long) : WishListUiEffect()
    object UnAuthorizedUserEffect : WishListUiEffect()
    object ClickDiscoverEffect : WishListUiEffect()
}
