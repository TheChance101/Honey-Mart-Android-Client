package org.the_chance.honeymart.ui.feature.see_all_markets

interface MarketInteractionListener {
    fun getAllMarkets()
    fun onClickMarket(marketId: Long)
    fun onclickTryAgainMarkets()
    fun onScrollDown()
}