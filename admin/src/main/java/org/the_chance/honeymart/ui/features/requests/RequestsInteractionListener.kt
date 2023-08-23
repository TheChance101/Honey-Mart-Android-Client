package org.the_chance.honeymart.ui.features.requests

interface RequestsInteractionListener {
    fun onGetAllRequests()
    fun onGetApproved()
    fun onClickRequest(position: Int)
    fun onClickCancel()
    fun onClickApprove()
    fun resetSnackBarState()
    fun updateRequests(position: Long, isApproved: Boolean)
}