package org.the_chance.honeymart.ui.feature.market

import org.the_chance.honeymart.ui.base.BaseInteractionListener

interface MarketUiEffect : BaseInteractionListener {
    fun onClickMarket(marketId: Long)
    fun onGetAllMarkets()
}