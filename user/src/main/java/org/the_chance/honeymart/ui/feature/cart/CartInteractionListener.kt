package org.the_chance.honeymart.ui.feature.cart

import org.the_chance.ui.BaseInteractionListener

interface CartInteractionListener : BaseInteractionListener {
    fun onClickAddCountProductInCart(productId: Long)
    fun onClickMinusCountProductInCart(productId: Long)
    fun deleteCart(productId: Long)

}