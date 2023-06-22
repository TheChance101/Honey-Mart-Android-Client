package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.OrderEntity

data class OrdersUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val state: Boolean = false,
    val orders: List<OrderUiState> = emptyList(),
    val orderStates:OrderStates = OrderStates.PROCESSING
)

data class OrderUiState(
    val orderId: Long?= 1,
    val totalPrice: Double? = 0.0,
    val state: Int?= 0,
    val date: Long?= 1687259600016,
    val marketName: String?="",
    val imageUrl: String?="",
)

fun OrderEntity.toOrderUiState(): OrderUiState {
    return OrderUiState(
        orderId = orderId,
        totalPrice = totalPrice ,
        state = state,
        date = date,
        marketName = market.marketName,
        imageUrl = market.imageUrl
    )
}

enum class OrderStates{
    PROCESSING,DONE,CANCELED
}