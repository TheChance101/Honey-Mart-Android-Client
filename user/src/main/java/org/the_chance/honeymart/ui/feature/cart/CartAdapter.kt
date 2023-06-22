package org.the_chance.honeymart.ui.feature.cart

import org.the_chance.honeymart.ui.feature.uistate.CartListProductUiState
import org.the_chance.honeymart.ui.feature.uistate.CartUiState
import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.user.R

class CartAdapter
    (

    listener: CartInteractionListener,
) : BaseAdapter<CartListProductUiState>(listener) {
    override val layoutID = R.layout.item_cart
}
interface CartInteractionListener : BaseInteractionListener {
    fun onClickCart(productId: Long)
}