package org.the_chance.honeymart.ui.feature.product_details

import org.the_chance.honeymart.ui.feature.product.ProductUiEffect

sealed class ProductDetailsUiEffect {
    data class AddToCartSuccess(val message: String) : ProductDetailsUiEffect()
    data class AddToCartError(val error: Exception) : ProductDetailsUiEffect()
    object UnAuthorizedUserEffect : ProductDetailsUiEffect()
}
