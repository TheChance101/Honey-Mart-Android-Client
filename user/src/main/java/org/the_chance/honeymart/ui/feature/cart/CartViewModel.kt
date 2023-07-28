package org.the_chance.honeymart.ui.feature.cart

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.AddToCartUseCase
import org.the_chance.honeymart.domain.usecase.CheckoutUseCase
import org.the_chance.honeymart.domain.usecase.DeleteFromCartUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCartUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
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
        _state.update { it.copy(isLoading = true, isError = false) }
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
                error = null,
                products = cart.products,
                total = cart.total
            )
        }
    }

    private fun onGetAllCartError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }


    private fun incrementProductCountByOne(productId: Long) {
        val currentState = _state.value
        val updatedProducts = currentState.products.map { product ->
            if (product.productId == productId) {
                val currentCount = product.productCount ?: 0
                val newProductCount = if (currentCount >= 100) 100 else currentCount + 1
                product.copy(productCount = newProductCount)
            } else {
                product
            }
        }

        val updatedState = currentState.copy(products = updatedProducts)
        _state.value = updatedState

        updateProductCart(
            productId,
            updatedProducts.find { it.productId == productId }?.productCount ?: 0
        )
    }

    private fun decrementProductCountByOne(productId: Long) {
        val currentState = _state.value

        val updatedProducts = currentState.products.map { product ->
            if (product.productId == productId) {
                val currentCount = product.productCount ?: 1
                val newProductCount = if (currentCount > 1) currentCount - 1 else 1
                product.copy(productCount = newProductCount)
            } else {
                product
            }
        }
        val updatedState = currentState.copy(products = updatedProducts)
        _state.value = updatedState
        updateProductCart(
            productId,
            updatedProducts.find { it.productId == productId }?.productCount ?: 0
        )
    }

    private fun updateProductCart(productId: Long, count: Int) {
        tryToExecute(
            { addToCartUseCase(productId, count) },
            ::onUpdateProductInCartSuccess,
            ::onUpdateProductInCartError
        )
    }

    private fun onUpdateProductInCartSuccess(message: String) {
        _state.update {
            it.copy(isLoading = false, error = null)
        }
        getChosenCartProducts()
    }

    private fun onUpdateProductInCartError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun onClickAddCountProductInCart(productId: Long) {
        incrementProductCountByOne(productId)
    }

    override fun onClickMinusCountProductInCart(productId: Long) {
        decrementProductCountByOne(productId)

    }



    fun onClickOrderNowButton() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { checkout() },
            ::onCheckOutSuccess,
            ::onCheckOutFailed
        )
    }
 private fun onCheckOutSuccess(message: String) {
     _state.update { it.copy(isLoading = false, products = emptyList(), bottomSheetIsDisplayed = true) }
     viewModelScope.launch { _effect.emit(EventHandler(CartUiEffect.ClickOrderEffect)) }
 }

    private fun onCheckOutFailed(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    fun onClickDiscoverButton() {
        viewModelScope.launch { _effect.emit(EventHandler(CartUiEffect.ClickDiscoverEffect)) }
    }

     override fun deleteCart(position: Long) {
          _state.update { it.copy(isLoading = true) }
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
                error = null,
                products = emptyList()
            )
        }
        getChosenCartProducts()
    }

    private fun onDeleteFromCartError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error, isError = true) }
    }

    fun changeBottomSheetValue(){
        _state.update { it.copy( bottomSheetIsDisplayed = false) }
    }

}