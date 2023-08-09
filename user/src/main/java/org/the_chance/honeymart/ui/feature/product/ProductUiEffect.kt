package org.the_chance.honeymart.ui.feature.product

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class ProductUiEffect : BaseUiEffect {
    data class ClickProductEffect(val productId: Long, val categoryId: Long) : ProductUiEffect()
    object UnAuthorizedUserEffect : ProductUiEffect()
    data class AddedToWishListEffect(val message: String) : ProductUiEffect()
    object RemovedFromWishListEffect : ProductUiEffect()
}
