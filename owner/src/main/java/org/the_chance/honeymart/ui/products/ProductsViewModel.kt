package org.the_chance.honeymart.ui.products

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.GetAllProductsByCategoryUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getAllProducts: GetAllProductsByCategoryUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProductsUiState, ProductsUiEffect>(ProductsUiState()) {
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
            { getAllProducts(1) },
            ::onGetProductsSuccess,
            ::onGetProductsError
        )
    }

    private fun onGetProductsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun onGetProductsSuccess(products: List<ProductEntity>) {
        _state.update { it.copy() }
        val productsUiState = products.map { product -> product.toProductUiState() }
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
        }
    }
}