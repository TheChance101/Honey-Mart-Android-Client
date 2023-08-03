package org.the_chance.honeymart.ui.feature.product_details

sealed class ProductDetailsUiEffect {
    object AddToCartSuccess : ProductDetailsUiEffect()
    data class AddToCartError(val error: Exception) : ProductDetailsUiEffect()
    object UnAuthorizedUserEffect :
        ProductDetailsUiEffect()

   object OnBackClickEffect : ProductDetailsUiEffect()

    object AddProductToWishListEffectSuccess : ProductDetailsUiEffect()
    data class AddProductToWishListEffectError(val error: Exception) : ProductDetailsUiEffect()
    object RemoveProductFromWishListEffectSuccess : ProductDetailsUiEffect()

    object RemoveProductFromWishListEffectError : ProductDetailsUiEffect()
    data class ProductNotInSameCartMarketExceptionEffect(val productId: Long,val count: Int) :
        ProductDetailsUiEffect()
}
