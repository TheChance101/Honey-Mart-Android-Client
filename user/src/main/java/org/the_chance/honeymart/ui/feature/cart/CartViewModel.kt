package org.the_chance.honeymart.ui.feature.cart

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.AddToCartUseCase
import org.the_chance.honeymart.domain.usecase.CheckoutUseCase
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
    private val addToCartUseCase: AddToCartUseCase,
    private val checkout:CheckoutUseCase
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

    private fun onGetAllCartError(
        throwable: Exception,
    ) {
        this._state.update {
            it.copy(isLoading = false, isError = true)
        }
    }

    private fun incrementProductCountByOne(productId: Long) {
        val currentState = _state.value

        val updatedProducts = currentState.products.map { product ->
            if (product.productId == productId) {
                val newProductCount = (product.productCount ?: 0) + 1
                product.copy(productCount = newProductCount)
            } else {
                product
            }

        }

        val updatedState = currentState.copy(products = updatedProducts)
        _state.value = updatedState

        // Call the updateProductCart function with the updated productId and productCount
        updateProductCart(
            productId,
            updatedProducts.find { it.productId == productId }?.productCount ?: 0
        )
        Log.e("Sara",updatedProducts.toString())
        Log.e("Sara","${updatedProducts.find { it.productId ==productId }?.productCount?:0}")
    }

    private fun decrementProductCountByOne(productId: Long) {
        val currentState = _state.value

        val updatedProducts = currentState.products.map { product ->
            if (product.productId == productId) {
                val currentCount = product.productCount ?: 0
                val newProductCount = if (currentCount > 0) currentCount - 1 else 0
                product.copy(productCount = newProductCount)
            } else {
                product
            }
        }
        val updatedState = currentState.copy(products = updatedProducts)
        _state.value = updatedState
        // Call the updateProductCart function with the updated productId and productCount
        updateProductCart(
            productId,
            updatedProducts.find { it.productId == productId }?.productCount ?: 0
        )
    }

    private fun updateProductCart(productId: Long, count: Int) {
        tryToExecute(
            { addToCartUseCase(productId, count)},
            ::onUpdateProductInCartSuccess,
            ::onUpdateProductInCartError
        )
    }

    private fun onUpdateProductInCartSuccess(message: String) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
            )
        }
        getChosenCartProducts()
    }

    private fun onUpdateProductInCartError(exception: Exception) {
        this._state.update {
            it.copy(isLoading = false, isError = true)
        }
    }

    override fun onClickAddCountProductInCart(productId: Long) {
        incrementProductCountByOne(productId)
    }

    override fun onClickMinusCountProductInCart(productId: Long) {
        decrementProductCountByOne(productId)

    }

    fun onClickOrderNowButton() {
        tryToExecute(
            { checkout() },
            ::onCheckOutSuccess,
            ::onCheckOutFailed
        )
         _state.update {
            it.copy(
                isLoading = true,
            )
        }
    }
 private fun onCheckOutSuccess(message: String) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                products = emptyList()
            )
        }
        viewModelScope.launch {
            _effect.emit(EventHandler(CartUiEffect.ClickOrderEffect))
        }
    }

    private fun onCheckOutFailed(exception: Exception) {
        this._state.update {
            it.copy(isLoading = false, isError = true)
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
                products = emptyList()
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