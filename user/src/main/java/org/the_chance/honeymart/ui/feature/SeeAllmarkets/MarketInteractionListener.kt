package org.the_chance.honeymart.ui.feature.SeeAllmarkets

interface MarketInteractionListener {
    fun getAllMarkets()
    fun onChangeMarketsScrollPosition(position: Int)
    fun onClickMarket(marketId: Long)
    fun onclickTryAgainMarkets()
}