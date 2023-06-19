package org.the_chance.honeymart.ui.feature.product_details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetProductByIdUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductsUiState
import org.the_chance.honeymart.ui.feature.uistate.toProductUiState
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductById: GetProductByIdUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProductsUiState, Long>(ProductsUiState()) {

    override val TAG: String = this::class.simpleName.toString()
    private val args = ProductDetailsArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getProductByCategoryId(args.productId, args.categoryId)
        log(args.productId.toString())
        log(_state.value.products.toString())
    }

    private fun getProductByCategoryId(productId: Long, categoryId: Long) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getProductById(productId, categoryId).map { it.toProductUiState() } },
            ::onGetProductSuccess,
            ::onGetProductError,
        )
    }

    private fun onGetProductSuccess(products: List<ProductUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                products = products
            )
        }
        log(_state.value.products.toString())
        log(products.toString())
    }

    private fun onGetProductError(throwable: Throwable) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = true,
            )
        }
    }
}