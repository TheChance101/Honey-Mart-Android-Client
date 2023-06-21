package org.the_chance.honeymart.ui.feature.cart

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.DeleteFromCartUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCartUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CartUiState
import org.the_chance.honeymart.ui.feature.uistate.toCartListProductUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCart: GetAllCartUseCase,
    private val deleteFromCart: DeleteFromCartUseCase,
) : BaseViewModel<CartUiState, CartUiEffect>(CartUiState()),
    CartInteractionListener {
    override val TAG: String = this::class.java.simpleName

    fun getChosenCartProducts() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllCart().toCartListProductUiState() },
            ::onGetAllCartSuccess,
            ::onGetAllCartError
        )
    }

    private fun onGetAllCartSuccess(cart: CartUiState) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                products = cart.products,
                total = cart.total
            )
        }
    }

    private fun onGetAllCartError(throwable: Exception) {
        this._state.update {
            it.copy(isLoading = false, isError = true)
        }
    }

    fun addProductCount(productCount: Int) {
        _state.update { currentState ->
            val updatedProducts = currentState.products.map { product ->
                    val newProductCount = productCount + 1
                    product.copy(productCount = newProductCount)
            }
            currentState.copy(products = updatedProducts)
        }

    }

    fun decrementProductCount(productCount: Int) {
        _state.update { currentState ->
            val updatedProducts = currentState.products.map { product ->
                    val newProductCount = if (productCount > 0) productCount - 1 else 0
                    product.copy(productCount = newProductCount)
            }
            currentState.copy(products = updatedProducts)
        }
    }

    override fun onClickCart(productId: Long) {

    }

    fun onClickOrderNowButton() {
        viewModelScope.launch {
            _effect.emit(EventHandler(CartUiEffect.ClickOrderEffect))
        }
    }

    fun onClickDiscoverButton() {
        viewModelScope.launch {
            _effect.emit(EventHandler(CartUiEffect.ClickDiscoverEffect))
        }
    }

    fun deleteCart(position: Long) {
        val productId = state.value.products[position.toInt()].productId
        viewModelScope.launch {
            if (productId != null) {
                tryToExecute(
                    { getAllCart().toCartListProductUiState() },
                    ::onGetAllCartSuccess,
                    ::onGetAllCartError
                )
                tryToExecute(
                    { deleteFromCart(productId) },
                    ::onDeleteFromCartSuccess,
                    ::onDeleteFromCartError
                )
            }
        }
    }

    private fun onDeleteFromCartSuccess(message: String) {
        _state.update {
            it.copy(
                isError = false,
            )
        }
        getChosenCartProducts()
    }

    private fun onDeleteFromCartError(throwable: Exception) {
        this._state.update {
            it.copy(isLoading = false, isError = true)
        }
    }


}