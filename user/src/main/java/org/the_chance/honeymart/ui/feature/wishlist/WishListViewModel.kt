package org.the_chance.honeymart.ui.feature.wishlist

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.DeleteFromWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.ui_effect.WishListUiEffect
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.WishListProductUiState
import org.the_chance.honeymart.ui.feature.uistate.WishListUiState
import org.the_chance.honeymart.ui.feature.uistate.toWishListProductUiState

@HiltViewModel
class WishListViewModel @javax.inject.Inject constructor(
    private val getAllWishListUseCase: GetAllWishListUseCase,
    private val deleteFromWishListUseCase: DeleteFromWishListUseCase,
) : BaseViewModel<WishListUiState, WishListUiEffect>(WishListUiState()),
    WishListInteractionListener {
    override val TAG: String = this::class.java.simpleName

    init {
        getWishListProducts()
    }

    private fun deleteProductFromWishList(productId: Long) {
        _state.update {
            it.copy(
                products = updateProductFavoriteState(false, productId),
                isLoading = true,
                isError = false
            )
        }
        tryToExecute(
            { deleteFromWishListUseCase(productId) },
            ::onDeleteProductSuccess,
            { onDeleteProductError(it, productId) }
        )
    }

    private fun onDeleteProductSuccess(successMessage: String) {
        executeAction(_effect, WishListUiEffect.DeleteProductFromWishListEffect)
        getWishListProducts()
    }

    private fun updateProductFavoriteState(
        isFavorite: Boolean,
        productId: Long
    ): List<WishListProductUiState> {
        val updatedProducts = _state.value.products.map { wishListProductUiState ->
            if (wishListProductUiState.productId == productId) {
                wishListProductUiState.copy(isFavorite = isFavorite)
            } else {
                wishListProductUiState
            }
        }
        return updatedProducts
    }


    private fun onDeleteProductError(error: ErrorHandler, productId: Long) {
        _state.update {
            it.copy(products = updateProductFavoriteState(true, productId), error = error)
        }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

   fun getWishListProducts() {
       _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getAllWishListUseCase().map { it.toWishListProductUiState() } },
            ::onGetProductSuccess,
            ::onGetProductError
        )
    }

    private fun onGetProductSuccess(products: List<WishListProductUiState>) {
        _state.update { it.copy(isLoading = false, error = null, products = products) }
    }

    private fun onGetProductError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    fun onClickDiscoverButton() {
        executeAction(_effect, WishListUiEffect.ClickDiscoverEffect)
    }

    override fun onClickFavoriteIcon(productId: Long) {
        deleteProductFromWishList(productId)
    }

    override fun onClickProduct(productId: Long) {
        executeAction(_effect, WishListUiEffect.ClickProductEffect(productId))
    }
}