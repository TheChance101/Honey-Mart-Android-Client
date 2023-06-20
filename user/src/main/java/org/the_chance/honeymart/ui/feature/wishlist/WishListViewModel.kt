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

    private fun getWishListProducts() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllWishListUseCase().map { it.toWishListProductUiState() } },
            ::onGetProductSuccess,
            ::onGetProductError
        )
    }

    private fun onGetProductSuccess(products: List<WishListProductUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                products = products
            )
        }
    }

    private fun onGetProductError(throwable: Exception) {
        _state.update { it.copy(isLoading = false, isError = true) }
    }

    override fun onClickProduct(productId: Long) {

    }

    fun onClickDiscoverButton() {
        viewModelScope.launch {
            _effect.emit(EventHandler(WishListUiEffect.ClickDiscoverEffect))
        }
    }

    override fun onClickAddToWishList(productId: Long) {
        viewModelScope.launch {
            deleteFromWishListUseCase(productId)
            getWishListProducts()
        }
    }
}