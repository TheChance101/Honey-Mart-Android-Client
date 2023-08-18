package org.the_chance.honeymart.ui.feature.home

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class HomeUiEffect :BaseUiEffect{
    data class NavigateToMarketScreen(val marketId: Long) : HomeUiEffect()

    data class NavigateToProductScreen(val productId: Long) : HomeUiEffect()

}