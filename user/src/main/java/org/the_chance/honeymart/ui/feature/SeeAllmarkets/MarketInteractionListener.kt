package org.the_chance.honeymart.ui.feature.SeeAllmarkets

interface MarketInteractionListener {
    fun getAllMarkets()
    fun onClickMarket(marketId: Long)
    fun onclickTryAgainMarkets()
    fun onScrollDown()
}