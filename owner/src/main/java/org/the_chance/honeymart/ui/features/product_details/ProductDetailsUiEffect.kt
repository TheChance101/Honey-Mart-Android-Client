package org.the_chance.honeymart.ui.features.product_details

sealed class ProductDetailsUiEffect {
    data class DeleteProductEffect(val productId: Long) : ProductDetailsUiEffect()
    data class UpdateProductEffect(val productId: Long) : ProductDetailsUiEffect()
}