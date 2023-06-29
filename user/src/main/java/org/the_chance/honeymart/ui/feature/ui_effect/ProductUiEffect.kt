package org.the_chance.honeymart.ui.feature.ui_effect

import org.the_chance.honeymart.util.AuthData

sealed class ProductUiEffect: BaseUiEffect(){
    data class ClickProductEffect(val productId: Long, val categoryId: Long) : ProductUiEffect()
    data class UnAuthorizedUserEffect(val authData: AuthData.Products) : ProductUiEffect()
    object AddedToWishListEffect : ProductUiEffect()
    object RemovedFromWishListEffect : ProductUiEffect()
}
