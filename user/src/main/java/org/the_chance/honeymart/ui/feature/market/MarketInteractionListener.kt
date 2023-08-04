package org.the_chance.honeymart.ui.feature.market

interface MarketInteractionListener {
    fun getChosenMarkets()
    fun onClickMarket(marketId: Long)
}