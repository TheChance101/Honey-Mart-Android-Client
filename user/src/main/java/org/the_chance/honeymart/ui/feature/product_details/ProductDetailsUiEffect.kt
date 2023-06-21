package org.the_chance.honeymart.ui.feature.product_details

sealed class ProductDetailsUiEffect {
    data class AddToCartSuccess(val message: String) : ProductDetailsUiEffect()
    data class AddToCartError(val error: Exception) : ProductDetailsUiEffect()
    object UnAuthorizedUserEffect : ProductDetailsUiEffect()

    object AddedToWishListEffect : ProductDetailsUiEffect()
    object RemovedFromWishListEffect : ProductDetailsUiEffect()
}
