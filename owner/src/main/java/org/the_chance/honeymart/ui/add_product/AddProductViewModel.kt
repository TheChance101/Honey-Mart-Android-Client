package org.the_chance.honeymart.ui.add_product

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.AddProductUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase
) : BaseViewModel<AddProductUiState, Unit>(AddProductUiState()), AddProductInteractionListener {
    override val TAG: String = this::class.java.simpleName
    private val args = 43L

    override fun addProduct(name: String, price: Double, description: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { addProductUseCase(name, price, description, args).toAddProductUiState() },
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
            )
        }
    }

    private fun onAddProductError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
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
        val productPriceState: ValidationState = when {
            price.isBlank() -> ValidationState.BLANK_TEXT_FIELD
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
}