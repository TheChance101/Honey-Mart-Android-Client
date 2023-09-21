package org.the_chance.honeymart.ui.feature.see_all_markets

interface MarketInteractionListener {
    fun getAllMarkets()
    fun onChangeMarketsScrollPosition(position: Int)
    fun onClickMarket(marketId: Long)
    fun onclickTryAgainMarkets()
}