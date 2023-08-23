package org.the_chance.honeymart.ui.features.orders

import org.the_chance.honeymart.domain.model.MarketOrderEntity
import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class OrdersUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val state: Boolean = false,
    val orders: List<OrderUiState> = emptyList(),
    val orderStates: OrderStates = OrderStates.ALL,
)

data class OrderUiState(
    val orderId: Long = 1,
    val totalPrice: Double = 0.0,
    val time: Long = 1687259600016,
    val userName: String = "",
    val state: Int = 0,
    )

fun MarketOrderEntity.toOrderUiState(): OrderUiState {
    return OrderUiState(
        orderId = orderId,
        totalPrice = totalPrice,
        state = state,
        time = date,
        userName = user.fullName
    )
}

enum class OrderStates(val state: Int) {
    ALL(0),
    PENDING(1),
    PROCESSING(2),
    DONE(3),
    CANCELED(5),



}

fun OrdersUiState.all() = this.orderStates == OrderStates.ALL
fun OrdersUiState.pending() = this.orderStates == OrderStates.PENDING
fun OrdersUiState.processing() =this.orderStates == OrderStates.PROCESSING
fun OrdersUiState.done() =this.orderStates == OrderStates.DONE
fun OrdersUiState.cancel() =this.orderStates == OrderStates.CANCELED

fun OrdersUiState.emptyOrdersPlaceHolder() = orders.isEmpty() && !this.isError && !this.isLoading

fun OrdersUiState.screenContent() = this.orders.isNotEmpty() && !this.isError
