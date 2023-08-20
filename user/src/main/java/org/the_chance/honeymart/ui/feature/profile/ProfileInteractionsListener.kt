package org.the_chance.honeymart.ui.feature.profile

interface ProfileInteractionsListener {

    fun onClickMyOrder()
    fun onClickCoupons()
    fun onClickNotification()
    fun onClickTheme()
    fun onClickLogout()

    fun showSnackBar(massage: String)
    fun showDialog()
    fun resetDialogState()

    fun updateImage(image: ByteArray)
    fun onImageSelected(image: ByteArray)

    fun getData()
}