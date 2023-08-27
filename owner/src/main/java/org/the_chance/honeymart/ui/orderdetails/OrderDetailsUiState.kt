package org.the_chance.honeymart.ui.orderdetails

import org.the_chance.honeymart.domain.model.OrderDetails
import org.the_chance.honeymart.domain.util.ErrorHandler

data class OrderDetailsUiState(
    val isProductsLoading: Boolean = false,
    val isDetailsLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val orderDetails: OrderParentDetailsUiState = OrderParentDetailsUiState(),
    val products: List<OrderDetailsProductUiState> = emptyList(),
)

data class OrderParentDetailsUiState(
    val orderId: Long = 0L,
    val totalPrice: Double = 0.0,
    val date: String = "",
    val state: Int = 1,
    val product: List<OrderDetailsProductUiState> = emptyList(),
)


data class OrderDetailsProductUiState(
    val id: Long = 0L,
    val name: String = "",
    val count: Int = 0,
    val price: Double = 0.0,
    val images: List<String> = emptyList(),
)

fun List<OrderDetails.ProductDetails>.toOrderDetailsProductUiState(): List<OrderDetailsProductUiState> {
    return map {
        OrderDetailsProductUiState(
            id = it.id,
            name = it.name,
            price = it.price,
            count = it.count,
            images = it.images
        )
    }
}

fun OrderDetails.toOrderParentDetailsUiState(): OrderParentDetailsUiState {
    return OrderParentDetailsUiState(
        totalPrice = totalPrice,
        state = state,
        date = date.toString(),
        orderId = orderId,
        product = products.toOrderDetailsProductUiState()
    )
}

fun OrderDetailsUiState.contentScreen() = !this.isLoading && !this.isError
fun OrderDetailsUiState.formatPrice(price: Double): String {
    return "$price$"
}

fun OrderDetailsUiState.formatOrder(order: Long): String {
    return "Order #${order}"
}