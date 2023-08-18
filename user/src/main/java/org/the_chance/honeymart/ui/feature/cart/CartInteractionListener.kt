package org.the_chance.honeymart.ui.feature.cart

interface CartInteractionListener {
    fun onClickAddCountProductInCart(productId: Long)
    fun onClickMinusCountProductInCart(productId: Long)
    fun deleteCart(productId: Long)
    fun hideBottomSheet()
    fun getChosenCartProducts()
    fun onClickDiscoverButton()

    fun onClickOrderNowButton()

    fun onClickViewOrders()
}