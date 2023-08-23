package org.the_chance.honeymart.ui.features.orders

interface OrdersInteractionsListener {
    fun onClickOrder(orderId: Long)
    fun getAllMarketOrders(orderState: OrderStates)
}