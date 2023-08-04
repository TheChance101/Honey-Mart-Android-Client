package org.the_chance.honeymart.ui.feature.cart

import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.model.CartProductsEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class CartUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val total: Double? = 0.0,
    val products: List<CartListProductUiState> = emptyList(),
    val bottomSheetIsDisplayed: Boolean = false
)

data class CartListProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productPrice: Double = 0.0,
    val productCount: Int = 0,
    val productImage: List<String> = emptyList()
)

fun CartEntity.toCartListProductUiState(): CartUiState {
    return CartUiState(
        total = total,
        products = products.toCartProductUiState()
    )
}

fun List<CartProductsEntity>.toCartProductUiState(): List<CartListProductUiState> {
    return this.map {
        CartListProductUiState(
            productId = it.id,
            productName = it.name,
            productPrice = it.price,
            productCount = it.count,
            productImage = it.images
        )
    }
}

fun CartUiState.unpopulatedLoading() = this.isLoading && this.products.isEmpty()

fun CartUiState.errorPlaceHolderCondition() = this.isError

fun CartUiState.placeHolderCondition() = this.products.isEmpty() && !this.isError && !this.isLoading

fun CartUiState.cartSuccessScreenCondition() = this.products.isNotEmpty() && !this.isError

fun CartUiState.populatedLoading() = this.isLoading && this.products.isNotEmpty()



