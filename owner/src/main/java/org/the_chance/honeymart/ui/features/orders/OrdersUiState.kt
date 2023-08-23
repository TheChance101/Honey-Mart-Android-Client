package org.the_chance.honeymart.ui.features.orders

import org.the_chance.honeymart.domain.model.MarketOrderEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//region Ui State
data class OrdersUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val orders: List<OrderUiState> = emptyList(),
    val orderStates: OrderStates = OrderStates.ALL,
)

data class OrderUiState(
    val orderId: Long = 1,
    val totalPrice: String = "",
    val time: String = "",
    val userName: String = "",
    val isOrderSelected :Boolean = false ,
    val state: Int = 0,
)

enum class OrderStates(val state: Int) {
    ALL(0),
    PENDING(1),
    PROCESSING(2),
    DONE(3),
    CANCELED(5)
}
// endregion

// region Mappers
fun MarketOrderEntity.toOrderUiState(): OrderUiState {
    return OrderUiState(
        orderId = orderId,
        totalPrice = totalPrice.toPriceFormat(),
        state = state,
        time = date.toDateFormat(),
        userName = user.fullName
    )
}

fun Long.toDateFormat(): String {
    val dateFormat = SimpleDateFormat("d MMM hh:mm a", Locale.getDefault())
    val date = Date(this)

    return dateFormat.format(date)
}

fun Double.toPriceFormat(): String = "$this$"
// endregion

// region Extensions
fun OrdersUiState.all() = orderStates == OrderStates.ALL

fun OrdersUiState.pending() = orderStates == OrderStates.PENDING

fun OrdersUiState.processing() = orderStates == OrderStates.PROCESSING

fun OrdersUiState.done() = orderStates == OrderStates.DONE

fun OrdersUiState.cancel() = orderStates == OrderStates.CANCELED

fun OrdersUiState.emptyOrdersPlaceHolder() = orders.isEmpty() && !isError && !isLoading

fun OrdersUiState.screenContent() = orders.isNotEmpty() && !isError
// endregion