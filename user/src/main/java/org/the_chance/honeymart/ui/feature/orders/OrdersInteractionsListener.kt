package org.the_chance.honeymart.ui.feature.orders

interface OrdersInteractionsListener {

    fun getAllProcessingOrders()
    fun getAllDoneOrders()
    fun getAllCancelOrders()
    fun updateOrders(position: Long, orderState: Int)
}