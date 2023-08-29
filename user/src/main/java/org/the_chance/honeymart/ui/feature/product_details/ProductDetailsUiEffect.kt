package org.the_chance.honeymart.ui.feature.product_details

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed interface ProductDetailsUiEffect : BaseUiEffect {
    data class AddToCartSuccess(val message: String) : ProductDetailsUiEffect
    data class AddToCartError(val error: Exception) : ProductDetailsUiEffect
    object UnAuthorizedUserEffect : ProductDetailsUiEffect
    object OnBackClickEffect : ProductDetailsUiEffect
    data class AddProductToWishListEffectError(val error: Exception) : ProductDetailsUiEffect
    data class ProductNotInSameCartMarketExceptionEffect(val productId: Long, val count: Int) :
        ProductDetailsUiEffect
}