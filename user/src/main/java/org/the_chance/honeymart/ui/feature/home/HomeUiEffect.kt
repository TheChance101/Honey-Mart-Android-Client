package org.the_chance.honeymart.ui.feature.home

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class HomeUiEffect : BaseUiEffect {
    object UnAuthorizedUserEffect : HomeUiEffect()

    data class NavigateToMarketScreenEffect(val marketId: Long) : HomeUiEffect()

    data class NavigateToProductScreenEffect(val productId: Long) : HomeUiEffect()
    object NavigateToSearchScreenEffect : HomeUiEffect()

}