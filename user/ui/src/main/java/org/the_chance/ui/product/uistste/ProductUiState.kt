package org.the_chance.ui.product.uistste

data class ProductUiState (
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val message: String = "",
    val productList: List<Product> = emptyList(),
    val categoryList: List<Category> = emptyList(),
)
data class Product(
    val id: Long = 0,
    val price: Double = 0.0,
    val name: String = "",
    val quantity: String = "",
    val isFavorite: Boolean = false
)

data class Category(
    val categoryId: Long = 0,
    val imageId: Int = 0,
    val categoryName: String = ""
)