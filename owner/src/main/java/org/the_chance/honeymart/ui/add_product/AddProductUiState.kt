package org.the_chance.honeymart.ui.add_product

import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState

data class AddProductUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val productName: String = "",
    val productPrice: String = "",
    val productDescription: String = "",
    val productImages: List<String> = emptyList(),
    val productNameState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val productPriceState: ValidationState = ValidationState.VALID_TEXT_FIELD,
    val productDescriptionState: ValidationState = ValidationState.VALID_TEXT_FIELD,
)

fun ProductEntity.toAddProductUiState(): AddProductUiState {
    return AddProductUiState(
        productName = productName,
        productPrice = ProductPrice.toString(),
        productDescription = productDescription,
        productImages = productImages
    )
}

fun AddProductUiState.showButton() =
    productName.isNotBlank()
            && productPrice.isNotBlank()
            && productDescription.isNotBlank()
            && !isLoading
            && productNameState == ValidationState.VALID_TEXT_FIELD
            && productPriceState == ValidationState.VALID_TEXT_FIELD
            && productDescriptionState == ValidationState.VALID_TEXT_FIELD
