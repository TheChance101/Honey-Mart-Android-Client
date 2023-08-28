package org.the_chance.honeymart.ui.features.markets

interface MarketsInteractionListener {
    fun onGetFilteredRequests(isApproved: Boolean)
    fun onClickRequest(position: Int)
    fun onClickCancel(marketId: Int)
    fun onClickApprove(marketId: Int)
    fun updateMarket(marketId: Int, isApproved: Boolean)
}