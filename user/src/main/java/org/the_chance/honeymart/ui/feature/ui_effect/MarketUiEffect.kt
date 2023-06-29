package org.the_chance.honeymart.ui.feature.ui_effect

sealed class MarketUiEffect: BaseUiEffect() {
    data class ClickDiscoverMarketsEffect(val MarketId: Long) : MarketUiEffect()
}