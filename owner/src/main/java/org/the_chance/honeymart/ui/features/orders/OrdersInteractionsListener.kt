package org.the_chance.honeymart.ui.features.orders

interface OrdersInteractionsListener {
    fun onClickOrder(orderDetails: OrderUiState, id: Long)
    fun getAllMarketOrders(orderState: OrderStates)
    fun onClickProduct(product: OrderDetailsProductUiState)
    fun updateStateOrder(id: Long?, updateState: OrderStates)
}