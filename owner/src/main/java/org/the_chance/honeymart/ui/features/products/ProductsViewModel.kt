package org.the_chance.honeymart.ui.features.products

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.AddProductImagesUseCase
import org.the_chance.honeymart.domain.usecase.AddProductUseCase
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getAllProducts: GetAllProductsByCategoryUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val addProductImagesUseCase: AddProductImagesUseCase,
    savedStateHandle: SavedStateHandle,

    ) : BaseViewModel<ProductsUiState, ProductsUiEffect>(ProductsUiState()),
    ProductsInteractionsListener {
    override val TAG: String
        get() = this::class.simpleName.toString()

    init {
        getData()
    }

    private fun getData() {
        _state.update { it.copy(error = null, isError = false) }
        getProductsByCategoryId()
    }

    private fun getProductsByCategoryId() {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getAllProducts(9) },
            ::onGetProductsSuccess,
            ::onGetProductsError
        )
    }

    private fun onGetProductsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
            Log.e("sara",state.value.products.toString())

        }
    }

    private fun onGetProductsSuccess(products: List<ProductEntity>) {
        _state.update { it.copy(isLoading = false) }
        val productsUiState = products.map { product -> product.toProductUiState() }
        Log.e("sara",productsUiState.toString())
        checkIfCategoryProductsEmpty(productsUiState)
    }

    private fun checkIfCategoryProductsEmpty(productsUiState: List<ProductUiState>) {
        if (productsUiState.isEmpty()) {
            _state.update {
                it.copy(
                    isEmptyProducts = true,
                    products = productsUiState,
                    productsQuantity = productsUiState.size.toString() + " Products"
                )
            }
        } else {
            _state.update {
                it.copy(
                    isEmptyProducts = false,
                    products = productsUiState,
                    productsQuantity = productsUiState.size.toString() + " Products"
                )
            }
            Log.e("Sara",productsUiState.toString())
        }
    }

    private val categoryId = 43L

    override fun addProduct(product: ProductsUiState) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                addProductUseCase(
                    product.name,
                    product.price.toDouble(),
                    product.description,
                    categoryId
                )
            },
            onSuccess = ::onAddProductSuccess,
            onError = ::onAddProductError
        )
    }

    private fun onAddProductSuccess(product: ProductEntity) {
        _state.update { it.copy(error = null, id = product.productId) }
        addProductImage(productId = product.productId, images = _state.value.images)
    }

    private fun onAddProductError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun addProductImage(productId: Long, images: List<ByteArray>) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { addProductImagesUseCase(productId, images) },
            onSuccess = { onAddProductImagesSuccess() },
            onError = ::onAddProductImagesError
        )
    }

    private fun onAddProductImagesSuccess() {
        _state.update { it.copy(isLoading = false, error = null) }
    }

    private fun onAddProductImagesError(errorHandler: ErrorHandler) {
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
        _state.update { it.copy(productNameState = productNameState, name = name) }
    }

    override fun onProductPriceChanged(price: String) {
        val productPriceState = getProductPriceState(price)
        _state.update { it.copy(productPriceState = productPriceState, price = price) }
    }

    private fun getProductPriceState(price: String): ValidationState {
        val priceRegex = Regex("^[0-9]+(\\.[0-9]+)?$")
        val productPriceState: ValidationState = when {
            price.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            !price.matches(priceRegex) -> ValidationState.INVALID_PRICE
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productPriceState
    }

    override fun onProductDescriptionChanged(description: String) {
        val productDescriptionState = getProductDescriptionState(description)
        _state.update {
            it.copy(productDescriptionState = productDescriptionState, description = description)
        }
    }

    private fun getProductDescriptionState(description: String): ValidationState {
        val productDescriptionState: ValidationState = when {
            description.isBlank() -> ValidationState.BLANK_TEXT_FIELD
            description.length < 6 -> ValidationState.SHORT_LENGTH_TEXT
            else -> ValidationState.VALID_TEXT_FIELD
        }
        return productDescriptionState
    }

    override fun onImagesSelected(uris: List<ByteArray>) {
        _state.update { it.copy(images = uris) }
    }

    override fun onClickRemoveSelectedImage(imageUri: ByteArray) {
        val updatedImages = _state.value.images.toMutableList()
        updatedImages.remove(imageUri)
        _state.update { it.copy(images = updatedImages) }
    }
}