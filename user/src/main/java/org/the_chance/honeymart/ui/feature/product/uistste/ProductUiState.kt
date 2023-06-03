package org.the_chance.ui.product.uistste

data class ProductsUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val productList: List<ProductUiState> = emptyList(),
    val categoryList: List<CategoryUiState> = emptyList(),
)

data class ProductUiState(
    val id: Long = 0,
    val price: Double = 0.0,
    val name: String = "",
    val quantity: String = "",
    val isFavorite: Boolean = false
)

data class CategoryUiState(
    val categoryId: Long = 0,
    val imageId: Int = 0,
    val categoryName: String = ""
)