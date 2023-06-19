package org.the_chance.honeymart.ui.feature.orderdetails

import org.the_chance.honeymart.ui.feature.uistate.OrderDetailsProductUiState
import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.user.R

class OrderDetailsAdapter(listener: OrderDetailsInteractionListener) :
    BaseAdapter<OrderDetailsProductUiState>(listener) {
    override val layoutID: Int = R.layout.item_order_details
}

interface OrderDetailsInteractionListener : BaseInteractionListener {
    fun onClickProduct(productId: Long)
}
