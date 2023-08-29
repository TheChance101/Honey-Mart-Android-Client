package org.the_chance.honeymart.ui.feature.home

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed interface HomeUiEffect : BaseUiEffect {
    object UnAuthorizedUserEffect : HomeUiEffect
    data class NavigateToMarketScreenEffect(val marketId: Long) : HomeUiEffect
    object NavigateToSeeAllMarketEffect : HomeUiEffect
    data class NavigateToProductsDetailsScreenEffect(val productId: Long) : HomeUiEffect
    object NavigateToSearchScreenEffect : HomeUiEffect

    object NavigateToNewProductsScreenEffect : HomeUiEffect
    data class NavigateToProductScreenEffect(
        val categoryId: Long,
        val marketId: Long,
        val position: Int
    ) : HomeUiEffect

    data class NavigateToOrderDetailsScreenEffect(val productId: Long) : HomeUiEffect
}