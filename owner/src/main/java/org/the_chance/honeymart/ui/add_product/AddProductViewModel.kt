package org.the_chance.honeymart.ui.add_product

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.AddProductWithImagesUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductWithImagesUseCase: AddProductWithImagesUseCase
) : BaseViewModel<AddProductUiState, Unit>(AddProductUiState()), AddProductInteractionListener {
    override val TAG: String = this::class.java.simpleName
    private val categoryId = 43L

    override fun addProduct(
        name: String,
        price: Double,
        description: String,
        images: List<String>
    ) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { addProductWithImagesUseCase(name, price, description, categoryId, images).toAddProductUiState() },
            onSuccess = ::onAddProductSuccess,
            onError = ::onAddProductError
        )
    }

    private fun onAddProductSuccess(product: AddProductUiState) {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                productName = product.productName,
                productPrice = product.productPrice,
                productDescription = product.productDescription,
                productImages = product.productImages
            )
        }
        log("SUCCESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS")
    }

    private fun onAddProductError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        log("Error occurred: $errorHandler")
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun onProductNameChanged(name: String) {
        val productNameState: ValidationState = when {
            name.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            name.length <= 2 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        _state.update { it.copy(productNameState = productNameState, productName = name) }
    }

    override fun onProductPriceChanged(price: String) {
        val priceRegex = Regex("^[0-9]+(\\.[0-9]+)?$")
        val productPriceState: ValidationState = when {
            price.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            !price.matches(priceRegex) -> ValidationState.INVALID_PRICE
            else -> ValidationState.VALID_TEXT_FIELD
        }
        _state.update { it.copy(productPriceState = productPriceState, productPrice = price) }
    }

    override fun onProductDescriptionChanged(description: String) {
        val productDescriptionState: ValidationState = when {
            description.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            description.length < 6 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        _state.update {
            it.copy(
                productDescriptionState = productDescriptionState,
                productDescription = description
            )
        }
    }

    override fun onImagesSelected(uris: List<String>) {
        _state.update { it.copy(productImages = uris) }
    }

    override fun onClickRemoveSelectedImage(imageUri: String) {
        val updatedImages = _state.value.productImages.toMutableList()
        updatedImages.remove(imageUri)
        _state.update { it.copy(productImages = updatedImages) }
    }
}