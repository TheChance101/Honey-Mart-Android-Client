package org.the_chance.honeymart.ui.features.orders

interface OrdersInteractionsListener {

    fun onClickOrder(orderId: Long)
    fun getAllOrders()
    fun getAllNewOrders()
    fun getAllProcessingOrders()
    fun getAllDoneOrders()
    fun getAllCancelOrders()
}