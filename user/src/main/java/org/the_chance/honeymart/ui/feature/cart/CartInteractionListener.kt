package org.the_chance.honeymart.ui.feature.cart

interface CartInteractionListener {
    fun onClickAddCountProductInCart(productId: Long)
    fun onClickMinusCountProductInCart(productId: Long)
    fun deleteCart(productId: Long)
    fun changeBottomSheetValue()

    fun getChosenCartProducts()
}