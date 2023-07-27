package org.the_chance.honeymart.ui.feature.orders

import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class OrdersUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val state: Boolean = false,
    val orders: List<OrderUiState> = emptyList(),
    val orderStates: OrderStates = OrderStates.PROCESSING,
)

data class OrderUiState(
    val orderId: Long? = 1,
    val totalPrice: Double? = 0.0,
    val state: Int? = 0,
    val date: Long? = 1687259600016,
    val marketName: String? = "",
    val imageUrl: String? = "",
    val quantity: Int? = 1
)

fun OrderEntity.toOrderUiState(): OrderUiState {
    return OrderUiState(
        orderId = orderId,
        totalPrice = totalPrice,
        state = state,
        date = date,
        marketName = market.marketName,
        imageUrl = market.imageUrl,
        quantity = numItems
    )
}

enum class OrderStates(val state:Int) {
    PROCESSING(1),
    DONE(2),
    CANCELED(3),
    DELETE(4)
}