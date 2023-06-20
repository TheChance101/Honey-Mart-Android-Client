package org.the_chance.honeymart.ui.feature.product_details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetProductByIdUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.ProductDetailsUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.honeymart.ui.feature.uistate.toProductUiState
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductById: GetProductByIdUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProductDetailsUiState, Long>(ProductDetailsUiState()) {

    override val TAG: String = this::class.simpleName.toString()
    private val args = ProductDetailsArgs.fromSavedStateHandle(savedStateHandle)

    init {
        getProductByCategoryId(args.productId, args.categoryId)
    }

    private fun getProductByCategoryId(productId: Long, categoryId: Long) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getProductById(productId, categoryId).toProductUiState() },
            ::onGetProductSuccess,
            ::onGetProductError,
        )
    }

    private fun onGetProductSuccess(product: ProductUiState) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                product = product
            )
        }
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