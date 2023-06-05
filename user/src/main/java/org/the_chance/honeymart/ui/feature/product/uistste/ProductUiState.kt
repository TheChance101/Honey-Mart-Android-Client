package org.the_chance.honeymart.ui.feature.product.uistste

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
    val isFavorite: Boolean = false
)

data class CategoryUiState(
    val categoryId: Long = 0L,
    val imageId: Int = 0,
    val categoryName: String = ""
)