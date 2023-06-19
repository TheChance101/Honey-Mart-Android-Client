package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import java.util.*

data class OrderDetailsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val products: List<OrderDetailsProductUiState> = emptyList(),
)

data class OrderDetailsProductUiState(
    val products: List<Any>? = null,  // **********************
    val totalPrice: Double? = 0.0,
    val date: Date?= null,       // ****************
    val state: Long? = 0
)

fun OrderDetailsEntity.OrderProductUiState(): OrderDetailsProductUiState {
    return OrderDetailsProductUiState(
        products = products,
        totalPrice = totalPrice,
        date = date,
        state = state
    )
}