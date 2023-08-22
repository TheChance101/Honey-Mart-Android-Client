package org.the_chance.honeymart.ui.features.requests

interface RequestsInteractionListener {
    fun onGetAllRequests()
    fun onGetNewRequests()
    fun onGetApproved()
    fun onClickRequest()
    fun onClickCancel()
    fun onClickApprove()
    fun resetSnackBarState()
    fun updateRequests(position: Long, orderState: Int)
}