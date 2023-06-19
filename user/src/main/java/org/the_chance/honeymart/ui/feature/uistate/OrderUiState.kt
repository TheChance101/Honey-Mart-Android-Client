package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.OrderEntity

data class OrdersUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val orders: List<OrderUiState> = emptyList()
)

data class OrderUiState(
    val orderId: Int? = 0,
    val marketName: String? = "",
    val orderQuantity: String? = "",
    val orderPrice: Double? = 0.0
)

fun OrderEntity.toOrderUiState(): OrderUiState {
    return OrderUiState(
        orderId = orderId,
        marketName = marketName,
        orderQuantity = orderQuantity,
        orderPrice = orderPrice
    )
}