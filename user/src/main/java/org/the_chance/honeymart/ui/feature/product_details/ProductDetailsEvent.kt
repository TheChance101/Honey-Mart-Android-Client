package org.the_chance.honeymart.ui.feature.product_details

sealed class ProductDetailsEvent {
    data class AddToCartSuccess(val message: String) : ProductDetailsEvent()
    data class AddToCartError(val error: Exception) : ProductDetailsEvent()
}
