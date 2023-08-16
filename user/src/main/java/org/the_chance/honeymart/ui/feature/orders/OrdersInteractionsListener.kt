package org.the_chance.honeymart.ui.feature.orders

interface OrdersInteractionsListener {

    fun onClickOrder(orderId: Long)
    fun onClickDiscoverMarkets()
    fun getAllPendingOrders()
    fun getAllProcessingOrders()
    fun getAllDoneOrders()
    fun getAllCancelOrders()
    fun updateOrders(position: Long, orderState: Int)
}