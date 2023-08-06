package org.the_chance.honeymart.ui.add_product

import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.util.ErrorHandler

data class AddProductUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val productName: String = "",
    val productPrice: String = "",
    val productDescription: String = "",
    val productImages: List<String> = emptyList(),
)

fun ProductEntity.toAddProductUiState(): AddProductUiState {
    return AddProductUiState(
        productName = productName,
        productPrice = ProductPrice.toString(),
        productDescription = productDescription,
        productImages = productImages
    )
}
