package org.the_chance.honeymart.ui.features.markets

interface MarketsInteractionListener {
    fun onClickTryAgain()
    fun onClickMarket(position: Int)
    fun onClickCancel(marketId: Int)
    fun onClickApprove(marketId: Int)
    fun updateMarket(marketId: Int, isApproved: Boolean)
    fun onClickMarketsState(state: MarketsState)
    fun onRefresh()
}