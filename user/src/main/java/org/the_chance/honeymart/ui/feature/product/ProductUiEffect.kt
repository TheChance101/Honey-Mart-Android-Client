package org.the_chance.honeymart.ui.feature.product

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class ProductUiEffect: BaseUiEffect {
    data class ClickProductEffect(val productId: Long, val categoryId: Long) : ProductUiEffect()
    object  UnAuthorizedUserEffect : ProductUiEffect()
    object AddedToWishListEffect : ProductUiEffect()
    object RemovedFromWishListEffect : ProductUiEffect()
}
