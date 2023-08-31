package org.the_chance.honeymart.ui.features.profile

interface ProfileInteractionListener {

    fun getPersonalInfo()
    fun getMarketInfo()
    fun updateMarketStatus(status: Int)
    fun dismessStatusDialog()
    fun showStatusDialog()
}