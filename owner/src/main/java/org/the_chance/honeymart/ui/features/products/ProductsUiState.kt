package org.the_chance.honeymart.ui.features.products

import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState

data class ProductsUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmptyProducts: Boolean = false,
    val error: ErrorHandler? = null,
    val products: List<ProductUiState> = emptyList(),
    val productsQuantity: String = "",
    val category: CategoryUiState = CategoryUiState(0, ""),
    val id: Long = 0L,
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val images: List<ByteArray> = emptyList(),
    val productNameState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val productPriceState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val productDescriptionState: ValidationState = ValidationState.VALID_TEXT_FIELD,
)

data class ProductUiState(
    val productId: Long = 0L,
    val productName: String = "",
    val productImage: String = "",
    val productPrice: String = "0.0",
)

data class CategoryUiState(
    val categoryIcon: Int = 0,
    val categoryName: String = ""
)
fun ProductsUiState.showButton(): Boolean {
    return name.isNotBlank()
            && price.isNotBlank()
            && description.isNotBlank()
            && !isLoading
            && productNameState == ValidationState.VALID_TEXT_FIELD
            && productPriceState == ValidationState.VALID_TEXT_FIELD
            && productDescriptionState == ValidationState.VALID_TEXT_FIELD
}

//fun ProductEntity.toProductUiState(): ProductUiState {
//    return ProductUiState(
//        productId = productId,
//        productName = productName,
//        productPrice = "$ProductPrice$",
//        productImage = productImages[0],
//    )
//}

fun ProductsUiState.contentScreen() = !this.isLoading && !this.isError
