package org.the_chance.honeymart.ui.feature.market

sealed class MarketUiEffect {
    data class ClickMarketEffect(val marketId: Long) : MarketUiEffect()
}