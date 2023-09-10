package org.the_chance.honeymart.ui.feature.profile

interface ProfileInteractionsListener {
    fun onClickMyOrder()
    fun onClickCoupons()
    fun onClickNotification()
    fun onClickLogout()
    fun showDialog()
    fun resetDialogState()
    fun updateImage(image: ByteArray)
    fun onImageSelected(image: ByteArray)
    fun getData()
    fun onClickLogin()
    fun onClickCameraIcon()
}