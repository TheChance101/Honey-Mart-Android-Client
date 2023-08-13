package org.the_chance.honeymart.ui.features.products

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class ProductsUiEffect: BaseUiEffect {
    data class ClickProductEffect(val productId: Long , val categoryId: Long) : ProductsUiEffect()
    data class DeleteProductEffect(val productId: Long)
    data class UpdateProductEffect(val productId: Long)
}
