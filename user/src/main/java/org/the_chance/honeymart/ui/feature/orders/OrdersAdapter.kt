package org.the_chance.honeymart.ui.feature.orders

import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.user.R

class OrdersAdapter(listener: OrderInteractionListener) : BaseAdapter<OrderUiState>(listener) {
    override val layoutID: Int = R.layout.item_order
}
interface OrderInteractionListener : BaseInteractionListener {
    fun onClickOrder(orderId: Long)
}