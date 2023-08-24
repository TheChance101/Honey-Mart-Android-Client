package org.the_chance.honeymart.ui.feature.new_products

import org.the_chance.honeymart.domain.model.RecentProductEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.product.ProductUiState


data class RecentProductsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val recentProducts: List<RecentProductUiState> = emptyList(),
    val discoverProducts: List<ProductUiState> = emptyList(),
)

data class RecentProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImage: String = "",
    val price: Double = 0.0,
    val isFavorite: Boolean = false
)

fun RecentProductEntity.toRecentProductUiState() = RecentProductUiState(
    productId = productId,
    productName = productName,
    productImage = productImages[0],
    price = productPrice,
    isFavorite = false,
)

fun RecentProductsUiState.screenContent() = this.recentProducts.isNotEmpty() && !this.isError

fun RecentProductsUiState.emptyRecentProductPlaceHolder() =
    this.recentProducts.isEmpty() && !this.isError && !this.isLoading