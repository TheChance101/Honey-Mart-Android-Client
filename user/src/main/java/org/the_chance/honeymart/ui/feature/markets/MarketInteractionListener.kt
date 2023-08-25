package org.the_chance.honeymart.ui.feature.markets

interface MarketInteractionListener {
    fun getAllMarkets()
    fun onClickMarket(marketId: Long)
    fun onclickTryAgainMarkets()
}