package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.model.CartProductsEntity

data class CartUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val total: Double? = 0.0,
    val products: List<CartListProductUiState> = emptyList(),
)

data class CartListProductUiState(
    val productId: Long? = 0L,
    val productName: String? = "",
    val productPrice: Double? = 0.0,
    val productCount: Int? = 0,
)

fun CartEntity.toCartListProductUiState(): CartUiState {
    return CartUiState(
        total = total,
        products = products?.toCartProductUiState() ?: emptyList()
    )
}

fun List<CartProductsEntity>.toCartProductUiState(): List<CartListProductUiState> {
    return this.map {
        CartListProductUiState(
            productId = it.id,
            productName = it.name,
            productPrice = it.price,
            productCount = it.count
        )
    }
}



