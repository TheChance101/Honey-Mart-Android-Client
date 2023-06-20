package org.the_chance.honeymart.ui.feature.product

sealed class ProductUiEffect {
    data class ClickProductEffect(val productId: Long) : ProductUiEffect()
    object UnAuthorizedUserEffect : ProductUiEffect()
    object AddedToWishListEffect : ProductUiEffect()
    object RemovedFromWishListEffect : ProductUiEffect()
}
