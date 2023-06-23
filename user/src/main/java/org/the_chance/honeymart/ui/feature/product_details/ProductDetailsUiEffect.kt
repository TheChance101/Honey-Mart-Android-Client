package org.the_chance.honeymart.ui.feature.product_details

import org.the_chance.honeymart.util.AuthData

sealed class ProductDetailsUiEffect {
    data class AddToCartSuccess(val message: String) : ProductDetailsUiEffect()
    data class AddToCartError(val error: Exception) : ProductDetailsUiEffect()
    data class UnAuthorizedUserEffect(val authData: AuthData.ProductDetails) : ProductDetailsUiEffect()
    data class AddProductToWishListEffectSuccess(val message: String) : ProductDetailsUiEffect()
    data class AddProductToWishListEffectError(val error: Exception) : ProductDetailsUiEffect()
    data class RemoveProductFromWishListEffectSuccess(val message: String) :
        ProductDetailsUiEffect()

    data class RemoveProductFromWishListEffectError(val error: Exception) : ProductDetailsUiEffect()
}
