package org.the_chance.honeymart.ui.feature.wishlist

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed interface WishListUiEffect : BaseUiEffect {
    data class ClickProductEffect(val productId: Long) : WishListUiEffect
    object ClickDiscoverEffect : WishListUiEffect
    data class DeleteProductFromWishListEffect(val message:String) : WishListUiEffect
}