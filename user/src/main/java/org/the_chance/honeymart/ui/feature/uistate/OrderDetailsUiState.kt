package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.OrderProductDetailsEntity

data class OrderDetailsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val orderDetails: OrderParentDetailsUiState = OrderParentDetailsUiState(),
    val products: List<OrderDetailsProductUiState> = emptyList(),
)

data class OrderParentDetailsUiState(
    val totalPrice: Double? = 0.0,
    val date: String? = "",
    val state: Int? = 0
)

data class OrderDetailsProductUiState(
    val id: Long? = 0L,
    val name: String? = "",
    val count: Int? = 0,
    val price: Double? = 0.0
)

fun OrderProductDetailsEntity.toOrderDetailsProductUiState(): OrderDetailsProductUiState {
    return OrderDetailsProductUiState(
        id = id,
        name = name,
        price = price,
        count = count
    )
}

fun OrderDetailsEntity.toOrderParentDetailsUiState(): OrderParentDetailsUiState {
    return OrderParentDetailsUiState(
        totalPrice = totalPrice,
        state = state,
        date = date
    )
}