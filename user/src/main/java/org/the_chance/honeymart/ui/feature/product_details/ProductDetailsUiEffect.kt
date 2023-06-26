package org.the_chance.honeymart.ui.feature.product_details

import org.the_chance.honeymart.util.AuthData

sealed class ProductDetailsUiEffect {
    object AddToCartSuccess : ProductDetailsUiEffect()
    data class AddToCartError(val error: Exception) : ProductDetailsUiEffect()
    data class UnAuthorizedUserEffect(val authData: AuthData.ProductDetails) :
        ProductDetailsUiEffect()

    object AddProductToWishListEffectSuccess : ProductDetailsUiEffect()
    data class AddProductToWishListEffectError(val error: Exception) : ProductDetailsUiEffect()
    object RemoveProductFromWishListEffectSuccess : ProductDetailsUiEffect()

    object RemoveProductFromWishListEffectError : ProductDetailsUiEffect()
    data class ProductNotInSameCartMarketExceptionEffect(val productId: Long,val count: Int) :
        ProductDetailsUiEffect()
}
