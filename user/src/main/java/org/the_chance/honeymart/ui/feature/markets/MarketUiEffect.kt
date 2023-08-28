package org.the_chance.honeymart.ui.feature.markets

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class MarketUiEffect:BaseUiEffect {
    data class ClickMarketEffect(val marketId: Long) : MarketUiEffect()
}