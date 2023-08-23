package org.the_chance.honeymart.ui.features.orders

interface OrdersInteractionsListener {

    fun onClickOrder(orderId: Long)
    fun getAllOrders()
    fun getAllPendingOrders()
    fun getAllProcessingOrders()
    fun getAllDoneOrders()
    fun getAllCancelOrders()
}