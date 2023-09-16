package org.the_chance.honeymart.ui.features.orders

import android.icu.text.DecimalFormat
import arrow.optics.optics
import org.the_chance.honeymart.domain.model.Market
import org.the_chance.honeymart.domain.model.OrderDetails
import org.the_chance.honeymart.domain.util.ErrorHandler
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//region Ui State
@optics
data class OrdersUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val orders: List<OrderUiState> = emptyList(),
    val orderStates: Int = OrderStates.ALL.state,
    val states: OrderStates = OrderStates.ALL,
    val orderDetails: OrderUiState = OrderUiState(),
    val products: List<OrderDetailsProductUiState> = emptyList(),
    val product: OrderDetailsProductUiState = OrderDetailsProductUiState(),
    val showState: ShowState = ShowState(),
    val orderId: Long = 0,
) {
    companion object
}

@optics
data class ShowState(
    val showProductDetails: Boolean = false,
    val showOrderDetails: Boolean = false,
) {
    companion object
}

@optics
data class OrderUiState(
    val orderId: Long = 1,
    val totalPrice: String = "",
    val time: String = "",
    val userName: String = "",
    val isOrderSelected: Boolean = false,
    val state: Int = 0,
    val isSelected: Boolean = false,
    val buttonsState: ButtonsState = ButtonsState()
) {
    companion object
}

@optics
data class OrderDetailsProductUiState(
    val id: Long = 0L,
    val name: String = "",
    val count: Int = 0,
    val price: Double = 0.0,
    val images: List<String> = emptyList(),
) {
    companion object

    val productImageUrl = images.takeIf { it.isNotEmpty() }?.first() ?: ""
}

enum class OrderStates(val state: Int) {
    ALL(0),
    PENDING(1),
    PROCESSING(2),
    DONE(3),
    CANCELED(5)
}

@optics
data class ButtonsState(
    val confirmText: String = "",
    val cancelText: String = "",
    val onClickConfirm: () -> Unit = {},
    val onClickCancel: () -> Unit = {}
) {
    companion object
}

// endregion

// region Mappers
fun Market.Order.toOrderUiState(): OrderUiState {
    return OrderUiState(
        orderId = orderId,
        totalPrice = totalPrice.toPriceFormat(),
        state = state,
        time = date.toDateFormat(),
        userName = user.fullName
    )
}

fun Date.toDateFormat(): String {
    val dateFormat = SimpleDateFormat("d MMM hh:mm a", Locale.getDefault())
    return dateFormat.format(this)
}

fun Double.toPriceFormat(): String {
    val decimalFormat = DecimalFormat("#,##0.0'$'")
    return decimalFormat.format(this)
}

fun OrderDetails.toOrderParentDetailsUiState(): OrderUiState {
    return OrderUiState(
        totalPrice = totalPrice.toPriceFormat(),
        state = state,
        time = date.toString(),
        orderId = orderId
    )
}

fun List<OrderDetails.ProductDetails>.toOrderDetailsProductUiState(): List<OrderDetailsProductUiState> {
    return map {
        OrderDetailsProductUiState(
            id = it.id,
            name = it.name,
            price = it.price,
            count = it.count,
            images = it.images.ifEmpty { listOf("", "") },
        )
    }
}

fun OrdersUiState.errorPlaceHolderCondition() = isError
fun OrdersUiState.contentScreen() = !this.isLoading && !this.isError
fun OrdersUiState.showOrdersState() =
    !showState.showProductDetails && !isError

fun OrdersUiState.showClickOrderPlaceHolder() =
    showOrdersState() && orders.isNotEmpty() && !isLoading

fun OrdersUiState.loadingScreen() =
    isLoading && !cancel() && !pending()
            && !processing() && orders.isNotEmpty() && !all() && !done()

fun OrdersUiState.emptyPlaceHolder() =
    orders.isEmpty() && all() && !isLoading

fun OrdersUiState.showOrderDetailsInRight() = orders.isNotEmpty()
        && products.isNotEmpty()
        && !showState.showProductDetails
        && showState.showOrderDetails


// endregion

// region Extensions
fun OrdersUiState.all() = states == OrderStates.ALL

fun OrdersUiState.pending() = states == OrderStates.PENDING

fun OrdersUiState.processing() = states == OrderStates.PROCESSING

fun OrdersUiState.done() = states == OrderStates.DONE

fun OrdersUiState.cancel() = states == OrderStates.CANCELED

fun OrdersUiState.emptyOrdersPlaceHolder() = orders.isEmpty() && !isError && !isLoading

fun OrdersUiState.showOrderDetails() = products.isNotEmpty() && showState.showProductDetails
fun OrdersUiState.showButtonState() = products.isNotEmpty() && !showState.showProductDetails
        && orderStates != OrderStates.DONE.state && orderStates != OrderStates.CANCELED.state
        && !isLoading
// endregion