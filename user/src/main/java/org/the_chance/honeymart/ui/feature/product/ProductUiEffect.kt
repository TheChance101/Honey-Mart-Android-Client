package org.the_chance.honeymart.ui.feature.product

import org.the_chance.honeymart.util.AuthData

sealed class ProductUiEffect {
    data class ClickProductEffect(val productId: Long, val categoryId: Long) : ProductUiEffect()
    data class UnAuthorizedUserEffect(val authData: AuthData.Products) : ProductUiEffect()
    object AddedToWishListEffect : ProductUiEffect()
    object RemovedFromWishListEffect : ProductUiEffect()
}
