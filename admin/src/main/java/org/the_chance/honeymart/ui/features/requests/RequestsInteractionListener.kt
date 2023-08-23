package org.the_chance.honeymart.ui.features.requests

interface RequestsInteractionListener {
    fun onGetFilteredRequests(isApproved: Boolean)
    fun onClickRequest(position: Int)
    fun onClickCancel(marketId: Int)
    fun onClickApprove(marketId: Int)
    fun resetSnackBarState()
    fun updateRequest(marketId: Int, isApproved: Boolean)
}