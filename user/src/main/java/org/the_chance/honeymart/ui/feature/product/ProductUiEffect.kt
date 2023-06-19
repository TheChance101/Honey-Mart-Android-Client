package org.the_chance.honeymart.ui.feature.product

sealed class ProductUiEffect {
    data class ClickProductEffect(val productId: Long, val categoryId: Long) : ProductUiEffect()
    object UnAuthorizedUserEffect : ProductUiEffect()
}
