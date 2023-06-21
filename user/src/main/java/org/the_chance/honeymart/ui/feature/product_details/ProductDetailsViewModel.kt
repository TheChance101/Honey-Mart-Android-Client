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
) : BaseViewModel<ProductDetailsUiState, Long>(ProductDetailsUiState()),
    ProductImageInteractionListener {

    override val TAG: String = this::class.simpleName.toString()
    private val args = ProductDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

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
                product = product,
                image = product.productImages?.first() ?: "",
                smallImages = product.productImages?.drop(1) as List<String>
            )
        }
    }

    private fun onGetProductError(throwable: Throwable) {
        _state.update { it.copy(isLoading = false, isError = true) }
    }

    override fun onClickImage(url: String) {
        val newList = mutableListOf<String>()
        newList.addAll(_state.value.smallImages.filter { it != url })
        newList.add(0, _state.value.image)
        _state.update { it.copy(smallImages = newList) }
        _state.update { it.copy(image = url) }
    }

    fun addProduct() {
        val currentQuantity = _state.value.quantity
        val newQuantity = currentQuantity + 1
        _state.update { it.copy(quantity = newQuantity) }
    }

    fun removeProduct() {
        val currentQuantity = _state.value.quantity
        val newQuantity = if (currentQuantity > 0) currentQuantity - 1 else 0
        _state.update { it.copy(quantity = newQuantity) }
    }
}