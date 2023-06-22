package org.the_chance.honeymart.ui.feature.uistate

import org.the_chance.honeymart.domain.model.WishListEntity

data class WishListUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val products: List<WishListProductUiState> = emptyList(),
)

data class WishListProductUiState(
    val productId: Long? = 0L,
    val productName: String? = "",
    val productPrice: Double? = 0.0,
    val isFavorite: Boolean? = true,
    val productImages: List<String>? = emptyList()
)

fun WishListEntity.toWishListProductUiState(): WishListProductUiState {
    return WishListProductUiState(
        productId = productId,
        productName = name,
        productPrice = price,
        productImages = images
    )
}