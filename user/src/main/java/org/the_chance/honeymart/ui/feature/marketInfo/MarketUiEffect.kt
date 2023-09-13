package org.the_chance.honeymart.ui.feature.marketInfo

import org.the_chance.honeymart.ui.base.BaseUiEffect


sealed interface MarketUiEffect : BaseUiEffect {
    data class ClickMarketEffect(val categoryId: Long, val marketId: Long, val position: Int) :
        MarketUiEffect
}