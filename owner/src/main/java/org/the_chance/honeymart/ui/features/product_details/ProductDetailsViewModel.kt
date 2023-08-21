package org.the_chance.honeymart.ui.features.product_details

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.GetProductDetailsUseCase
import org.the_chance.honeymart.domain.usecase.UpdateProductDetailsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productDetailsUseCase: GetProductDetailsUseCase,
    private val updateProductDetailsUseCase: UpdateProductDetailsUseCase

) : BaseViewModel<ProductsDetailsUiState, ProductDetailsUiEffect>(ProductsDetailsUiState()),
    ProductDetailsInteractionListener {
    override val TAG: String
        get() = this::class.simpleName.toString()

    init {
        getData()
    }

    private fun getData() {
        getProductDetails(28)
    }

    private fun getProductDetails(productId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { productDetailsUseCase(productId) },
            ::onGetProductDetailsSuccess,
            ::onGetProductDetailsError
        )
    }

    private fun onGetProductDetailsSuccess(productDetails: ProductEntity) {
        _state.update {
            it.copy(
                isLoading = false,
                productDetails = productDetails.toProductDetailsUiState()
            )
        }
    }

    private fun onGetProductDetailsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun updateProductDetails(product: ProductsDetailsUiState) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            function = {
                updateProductDetailsUseCase(
                    id = product.id,
                    name = product.productName,
                    price = product.productPrice,
                    description = product.productDescription,
                )
            },
            onSuccess = { onUpdateProductDetailsSuccess() },
            ::onUpdateProductDetailsError
        )
    }

    private fun onUpdateProductDetailsSuccess() {
        _state.update { it.copy(isLoading = false, error = null) }
    }

    private fun onUpdateProductDetailsError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    private fun updateProductNameState(productName: String): ValidationState {
        val productNameState: ValidationState = when {
            productName.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            productName.length <= 2 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productNameState
    }

    override fun onUpdateProductName(productName: String) {
        val productNameState = updateProductNameState(productName)
        _state.update { it.copy(productNameState = productNameState, productName = productName) }
    }

    private fun updateProductPriceState(productPrice: String): ValidationState {
        val priceRegex = Regex("^[0-9]+(\\.[0-9]+)?$")
        val productPriceState: ValidationState = when {
            productPrice.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            !productPrice.matches(priceRegex) -> ValidationState.INVALID_PRICE
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productPriceState
    }

    override fun onUpdateProductPrice(productPrice: String) {
        val productPriceState = updateProductPriceState(productPrice)
        _state.update {
            it.copy(
                productPriceState = productPriceState,
                productPrice = productPrice
            )
        }
    }

    private fun updateProductDescriptionState(productDescription: String): ValidationState {
        val productDescriptionState: ValidationState = when {
            productDescription.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            productDescription.length < 6 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productDescriptionState
    }

    override fun onUpdateProductDescription(productDescription: String) {
        val productDescriptionState = updateProductDescriptionState(productDescription)
        _state.update {
            it.copy(
                productDescriptionState = productDescriptionState,
                productDescription = productDescription
            )
        }
    }

    override fun onUpdateProductImage(uris: List<ByteArray>) {

    }
}