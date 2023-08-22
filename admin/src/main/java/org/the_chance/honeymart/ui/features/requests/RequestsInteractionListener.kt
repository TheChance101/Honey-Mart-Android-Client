package org.the_chance.honeymart.ui.features.requests

interface RequestsInteractionListener {
    fun onClickAllRequests()
    fun onClickNewRequests()
    fun onClickApproved()
    fun onClickRequest()
    fun onClickCancel()
    fun onClickApprove()
    fun resetSnackBarState()
    fun updateRequests(position: Long, orderState: Int)
}