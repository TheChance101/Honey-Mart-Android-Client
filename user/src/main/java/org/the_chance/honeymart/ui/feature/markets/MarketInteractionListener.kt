package org.the_chance.honeymart.ui.feature.markets

interface MarketInteractionListener {
    fun getChosenMarkets()
    fun onClickMarket(marketId: Long)
}