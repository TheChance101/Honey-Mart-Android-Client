package org.the_chance.honeymart.ui.feature.product

import org.the_chance.honeymart.domain.model.Category
import org.the_chance.honeymart.domain.model.Market
import org.the_chance.honeymart.domain.model.Product
import org.the_chance.honeymart.ui.feature.market.MarketUiState

data class ProductsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val productList: List<ProductUiState> = emptyList(),
    val categoryList: List<CategoryUiState> = emptyList(),
)

data class ProductUiState(
    val id: Long = 0L,
    val price: Double = 0.0,
    val name: String = "",
    val quantity: String = "",
)

data class CategoryUiState(
    val categoryId: Long = 0L,
    val categoryName: String = ""
)

fun Product.asProductUiState(): ProductUiState {
    return ProductUiState(
        id = id,
        name = name,
        price = price,
        quantity = quantity
    )
}

fun Category.asCategoryUiState(): CategoryUiState {
    return CategoryUiState(
        categoryId = id,
        categoryName = name,
    )
}