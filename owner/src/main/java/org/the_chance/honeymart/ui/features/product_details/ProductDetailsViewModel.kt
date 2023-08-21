package org.the_chance.honeymart.ui.features.product_details

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.ProductEntity
import org.the_chance.honeymart.domain.usecase.GetProductDetailsUseCase
import org.the_chance.honeymart.domain.usecase.UpdateProductUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productDetailsUseCase: GetProductDetailsUseCase,
    private val updateProductUseCase: UpdateProductUseCase

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
                products = productDetails.toProductDetailsUiState()
            )
        }
    }

    private fun onGetProductDetailsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun onUpdateProductName(name: String) {
    }

    override fun onUpdateProductPrice(price: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateProductDescription(description: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateProductImage(uris: List<ByteArray>) {
        TODO("Not yet implemented")
    }
}