package org.the_chance.honeymart.ui.feature.ui_effect

sealed class WishListUiEffect: BaseUiEffect() {
    data class ClickProductEffect(val productId: Long) : WishListUiEffect()
    object UnAuthorizedUserEffect : WishListUiEffect()
    object ClickDiscoverEffect : WishListUiEffect()
    object DeleteProductFromWishListEffect : WishListUiEffect()
}
