package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.OrderProductDetailsEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class OrderDetailsUiState(
    val isProductsLoading: Boolean = false,
    val isDetailsLoading: Boolean = false,
    val error: ErrorHandler? = null,
    val isError: Boolean = false,
    val orderDetails: OrderParentDetailsUiState = OrderParentDetailsUiState(),
    val products: List<OrderDetailsProductUiState> = emptyList(),
)

data class OrderParentDetailsUiState(
    val totalPrice: Double? = 0.0,
    val date: String? = "",
    val state: Int? = 1,
){
    val  stateText = if(state == 1 ) "Processing" else  if(state == 2 ) "Done" else "Cancelled"
}

data class OrderDetailsProductUiState(
    val id: Long? = 0L,
    val name: String? = "",
    val count: Int? = 0,
    val price: Double? = 0.0,
    val images: List<String>? = emptyList(),
)

fun OrderProductDetailsEntity.toOrderDetailsProductUiState(): OrderDetailsProductUiState {
    return OrderDetailsProductUiState(
        id = id,
        name = name,
        price = price,
        count = count,
        images = images
    )
}

fun OrderDetailsEntity.toOrderParentDetailsUiState(): OrderParentDetailsUiState {
    return OrderParentDetailsUiState(
        totalPrice = totalPrice,
        state = state,
        date = date,
    )
}