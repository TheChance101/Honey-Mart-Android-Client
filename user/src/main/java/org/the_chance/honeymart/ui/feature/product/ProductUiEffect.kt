package org.the_chance.honeymart.ui.feature.product

sealed class ProductUiEffect {
    data class ClickProductEffect(val productId: Boolean) : ProductUiEffect()
    object UnAuthorizedUserEffect : ProductUiEffect()
}
