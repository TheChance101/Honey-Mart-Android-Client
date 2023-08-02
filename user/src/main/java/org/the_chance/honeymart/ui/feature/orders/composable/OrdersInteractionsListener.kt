package org.the_chance.honeymart.ui.feature.orders.composable

interface OrdersInteractionsListener {

    fun onClickProcessingOrder()
    fun onClickDoneOrder()
    fun onClickCancelOrder()
    fun onClickConfirmOrder(position: Long, orderState: Int)
}