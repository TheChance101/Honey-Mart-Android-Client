package org.the_chance.honeymart.ui.feature.wishlist

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.DeleteFromWishListUseCase
import org.the_chance.honeymart.domain.usecase.GetAllWishListUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.FavouriteItemState
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
    private val clickFav = MutableStateFlow(FavouriteItemState())

    init {
        getWishListProducts()
        updateFav()
    }

    private fun updateFav() {
        viewModelScope.launch {
            clickFav.debounce(1000).collect { favoriteItem ->
                val isFavorite = favoriteItem.isFavorite
                if (isFavorite) {
                    deleteProductFromWishList(favoriteItem.productId)
                } else {
                    log("FAVOURITE")
                }
            }
        }
    }

    private fun deleteProductFromWishList(productId: Long) {
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
        productId: Long,
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

    private fun getWishListProducts() {
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

    override fun onClickProduct(productId: Long) {}

    fun onClickDiscoverButton() {
        viewModelScope.launch {
            _effect.emit(EventHandler(WishListUiEffect.ClickDiscoverEffect))
        }
    }

    override fun onClickFavoriteIcon(productId: Long) {
        val currentProduct = _state.value.products.find { it.productId == productId }
        val isFavorite = currentProduct?.isFavorite ?: false
        val newFavoriteState = !isFavorite
        viewModelScope.launch {
            clickFav.emit(FavouriteItemState(productId, isFavorite))
        }
        updateFavoriteState(productId, newFavoriteState)
    }
    private fun updateFavoriteState(productId: Long, isFavorite: Boolean) {
        val newProduct = _state.value.products.map {
            if (it.productId == productId) {
                it.copy(isFavorite = isFavorite)
            } else {
                it
            }
        }
        _state.update { it.copy(products = newProduct) }
    }
}