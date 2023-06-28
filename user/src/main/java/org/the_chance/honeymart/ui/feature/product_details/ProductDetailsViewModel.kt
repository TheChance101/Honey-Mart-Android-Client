package org.the_chance.honeymart.ui.feature.product_details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.AddToCartUseCase
import org.the_chance.honeymart.domain.usecase.AddToWishListUseCase
import org.the_chance.honeymart.domain.usecase.DeleteAllCartUseCase
import org.the_chance.honeymart.domain.usecase.DeleteFromWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetIfProductInWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetProductDetailsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.ui_effect.ProductDetailsUiEffect
import org.the_chance.honeymart.ui.feature.uistate.ProductDetailsUiState
import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.honeymart.ui.feature.uistate.toProductUiState
import org.the_chance.honeymart.util.AuthData
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val addProductToCartUseCase: AddToCartUseCase,
    private val deleteCartUseCase: DeleteAllCartUseCase,
    private val addProductToWishListUseCase: AddToWishListUseCase,
    private val getIfProductInWishListUseCase: GetIfProductInWishListUseCase,
    private val deleteProductFromWishListUseCase: DeleteFromWishListUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<ProductDetailsUiState, ProductDetailsUiEffect>(ProductDetailsUiState()),
    ProductImageInteractionListener {

    override val TAG: String = this::class.simpleName.toString()

    private val args = ProductDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)


    init {
        getData()
    }

    fun getData() {
        getProductDetails(args.productId)
    }


    fun confirmDeleteLastCartAndAddProductToNewCart(productId: Long, count: Int) {
        _state.update { it.copy(error = null, isError = false) }
        tryToExecute(
            { deleteCartUseCase() },
            { onDeleteCartSuccess(it, productId, count) },
            ::onDeleteCartError,
        )
    }

    private fun onDeleteCartSuccess(message: String, productId: Long, count: Int) {
        addProductToCart(productId, count)
    }

    private fun onDeleteCartError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }


    // region Product
    private fun getProductDetails(productId: Long) {
        _state.update { it.copy(isLoading = true, error = null, isError = false) }
        tryToExecute(
            { getProductDetailsUseCase(productId).toProductUiState() },
            ::onGetProductSuccess,
            ::onGetProductError,
        )
    }

    private fun onGetProductSuccess(product: ProductUiState) {
        checkIfProductInWishList(args.productId)
        _state.update {
            it.copy(
                isLoading = false, error = null, isError = false, product = product,
                image = product.productImages?.first() ?: "",
                smallImages = product.productImages?.drop(1) as List<String>
            )
        }
    }

    private fun onGetProductError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun onClickImage(url: String) {
        val newList = mutableListOf<String>()
        newList.addAll(_state.value.smallImages.filter { it != url })
        newList.add(0, _state.value.image)
        _state.update { it.copy(smallImages = newList) }
        _state.update { it.copy(image = url) }
    }

    fun increaseProductCount() {
        val currentQuantity = _state.value.quantity
        val newQuantity = if (currentQuantity >= 100) 100 else currentQuantity + 1
        _state.update { it.copy(quantity = newQuantity) }
    }

    fun decreaseProductCount() {
        val currentQuantity = _state.value.quantity
        val newQuantity = if (currentQuantity > 1) currentQuantity - 1 else 1
        _state.update { it.copy(quantity = newQuantity) }
    }

    // endregion

    // region Cart

    fun addProductToCart(productId: Long, count: Int) {
        _state.update { it.copy(isAddToCartLoading = true, error = null, isError = false) }
        tryToExecuteDebounced(
            { addProductToCartUseCase(productId, count) },
            ::onAddProductToCartSuccess,
            { onAddProductToCartError(it, productId, count) }
        )
    }

    private fun onAddProductToCartSuccess(message: String) {
        _state.update { it.copy(isAddToCartLoading = false) }
        executeAction(_effect, ProductDetailsUiEffect.AddToCartSuccess)
    }

    private fun onAddProductToCartError(error: ErrorHandler, productId: Long, count: Int) {
        _state.update { it.copy(isLoading = false, isAddToCartLoading = false) }

        when (error) {
            is ErrorHandler.NoConnection -> {
                _state.update { it.copy(isError = true) }
            }

            is ErrorHandler.UnAuthorizedUser -> {
                executeAction(
                    _effect,
                    ProductDetailsUiEffect.UnAuthorizedUserEffect(
                        AuthData.ProductDetails(state.value.product.productId!!)
                    )
                )
            }

            is ErrorHandler.InvalidData -> {
                executeAction(
                    _effect,
                    ProductDetailsUiEffect.ProductNotInSameCartMarketExceptionEffect(
                        productId,
                        count
                    )
                )
            }

            else -> {}
        }
    }

    // endregion

    // region Wishlist


    // region Check If Product In Wishlist

    private fun checkIfProductInWishList(productId: Long) {
        _state.update { it.copy(isLoading = true, error = null, isError = false) }
        tryToExecute(
            { getIfProductInWishListUseCase(productId) },
            ::onGetIfProductInWishListSuccess,
            ::onGetIfProductInWishListError
        )
    }

    private fun onGetIfProductInWishListSuccess(isFavorite: Boolean) {
        updateFavoriteState(isFavorite)
    }

    private fun onGetIfProductInWishListError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    // endregion

    // region Add Product To Wishlist

    private fun addProductToWishList(productId: Long) {
        _state.update { it.copy(isLoading = true, error = null, isError = false) }
        tryToExecuteDebounced(
            { addProductToWishListUseCase(productId) },
            ::onAddProductToWishListSuccess,
            { error -> onAddProductToWishListError(error, productId) }
        )
    }

    private fun onAddProductToWishListSuccess(message: String) {
        _state.update {
            it.copy(isLoading = false)
        }
        executeAction(_effect, ProductDetailsUiEffect.AddProductToWishListEffectSuccess)
    }

    private fun onAddProductToWishListError(error: ErrorHandler, productId: Long) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        } else if (error is ErrorHandler.UnAuthorizedUser) {
            executeAction(
                _effect,
                ProductDetailsUiEffect.UnAuthorizedUserEffect(
                    AuthData.ProductDetails(state.value.product.productId!!)
                )
            )
        }
        updateFavoriteState(false)
    }

    // endregion

    // region Update Favorite State

    private fun updateFavoriteState(isFavorite: Boolean) {
        val updatedProductUiState = _state.value.product.copy(isFavorite = isFavorite)
        _state.update { it.copy(product = updatedProductUiState) }
    }

    fun onClickFavIcon(productId: Long) {
        val currentProduct = _state.value.product
        val isFavorite = currentProduct.isFavorite ?: false
        val newFavoriteState = !isFavorite

        updateFavoriteState(newFavoriteState)

        if (isFavorite) {
            deleteProductFromWishList(productId)
        } else {
            addProductToWishList(productId)
        }
    }

    // endregion

    // region Delete Product From WishList

    private fun deleteProductFromWishList(productId: Long) {
        _state.update { it.copy(error = null, isError = false) }
        tryToExecuteDebounced(
            { deleteProductFromWishListUseCase(productId) },
            ::onDeleteWishListSuccess,
            ::onDeleteWishListError
        )
    }


    private fun onDeleteWishListSuccess(successMessage: String) {
    executeAction(_effect, ProductDetailsUiEffect.RemoveProductFromWishListEffectSuccess)
    }

    private fun onDeleteWishListError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

}