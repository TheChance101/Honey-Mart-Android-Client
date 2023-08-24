package org.the_chance.honeymart.ui.feature.home

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class HomeUiEffect : BaseUiEffect {
    object UnAuthorizedUserEffect : HomeUiEffect()

    data class NavigateToMarketScreenEffect(val marketId: Long) : HomeUiEffect()
    object NavigateToSeeAllMarketEffect : HomeUiEffect()
    data class NavigateToProductsDetailsScreenEffect(val productId: Long) : HomeUiEffect()
    object NavigateToSearchScreenEffect : HomeUiEffect()
    data class NavigateToProductScreenEffect(
        val categoryId: Long,
        val marketId: Long,
        val position: Int
    ) : HomeUiEffect()

}