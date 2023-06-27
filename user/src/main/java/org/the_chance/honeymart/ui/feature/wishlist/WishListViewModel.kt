package org.the_chance.honeymart.ui.feature.wishlist

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.DeleteFromWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.WishListProductUiState
import org.the_chance.honeymart.ui.feature.uistate.WishListUiState
import org.the_chance.honeymart.ui.feature.uistate.toWishListProductUiState
import org.the_chance.honeymart.util.EventHandler

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
        _state.update { it.copy(products = updateProductFavoriteState(false, productId), isLoading = true) }
        tryToExecute(
            { deleteFromWishListUseCase(productId) },
            ::onDeleteProductSuccess,
            { onDeleteProductError(it, productId) }
        )
    }

    private fun onDeleteProductSuccess(successMessage: String) {
        viewModelScope.launch {
            _effect.emit(EventHandler(WishListUiEffect.DeleteProductFromWishListEffect))
        }
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


    private fun onDeleteProductError(exception: Exception, productId: Long) {
        _state.update { it.copy(products = updateProductFavoriteState(true, productId)) }
        // emit anything to observe on Fragment to show snackbar or to show vector something went wrong
        // this based on caught exception(Very important , may be internet or timeout , or server error)
    }

   fun getWishListProducts() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllWishListUseCase().map { it.toWishListProductUiState() } },
            ::onGetProductSuccess,
            ::onGetProductError
        )
    }

    private fun onGetProductSuccess(products: List<WishListProductUiState>) {
        _state.update { it.copy(isLoading = false, isError = false, products = products) }
    }

    private fun onGetProductError(throwable: Exception) {
        _state.update { it.copy(isLoading = false, isError = true) }
    }

    fun onClickDiscoverButton() {
        viewModelScope.launch {
            _effect.emit(EventHandler(WishListUiEffect.ClickDiscoverEffect))
        }
    }

    override fun onClickFavoriteIcon(productId: Long) {
        deleteProductFromWishList(productId)
    }

    override fun onClickProduct(productId: Long) {
        viewModelScope.launch {
            _effect.emit(
                EventHandler(
                    WishListUiEffect.ClickProductEffect(
                        productId
                    )
                )
            )
        }
    }
}