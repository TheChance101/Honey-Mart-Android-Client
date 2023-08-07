package org.the_chance.honeymart.ui.orders

interface OrdersInteractionsListener {

    fun onClickOrder(orderId: Long)
    fun getAllOrders()
    fun getAllNewOrders()
    fun getAllProcessingOrders()
    fun getAllDoneOrders()
    fun getAllCancelOrders()
}