package org.the_chance.honeymart.ui.feature.product_details

sealed class ProductDetailsUiEffect {
    data class AddToCartSuccess(val message: String) : ProductDetailsUiEffect()
    data class AddToCartError(val error: Exception) : ProductDetailsUiEffect()
    object UnAuthorizedUserEffect : ProductDetailsUiEffect()
    data class AddProductToWishListEffectSuccess(val message: String) : ProductDetailsUiEffect()
    data class AddProductToWishListEffectError(val error: Exception) : ProductDetailsUiEffect()
    data class RemoveProductFromWishListEffectSuccess(val message: String) : ProductDetailsUiEffect()
    data class RemoveProductFromWishListEffectError(val error: Exception) : ProductDetailsUiEffect()
}
