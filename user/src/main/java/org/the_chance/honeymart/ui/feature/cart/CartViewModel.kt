package org.the_chance.honeymart.ui.feature.cart

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.CartEntity
import org.the_chance.honeymart.domain.usecase.CartUseCase
import org.the_chance.honeymart.domain.usecase.CheckoutUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase,
    private val checkout: CheckoutUseCase
) : BaseViewModel<CartUiState, CartUiEffect>(CartUiState()),
    CartInteractionListener {
    override val TAG: String = this::class.java.simpleName

    override fun getChosenCartProducts() {
        _state.update { it.copy(isLoading = true, isError = false, bottomSheetIsDisplayed = false) }
        tryToExecute(
            cartUseCase::getCart,
            ::onGetAllCartSuccess,
            ::onGetAllCartError
        )
    }

    private fun onGetAllCartSuccess(cart: CartEntity) {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                products = cart.products.toCartProductUiState(),
                total = cart.total,
            )
        }
    }

    private fun onGetAllCartError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
        if (error is ErrorHandler.UnAuthorizedUser){
            refreshToken()
        }
    }


    private fun incrementProductCountByOne(productId: Long) {
        val currentState = _state.value
        val updatedProducts = currentState.products.map { product ->
            if (product.productId == productId) {
                val currentCount = product.productCount
                val newProductCount = if (currentCount >= 100) 100 else currentCount + 1
                product.copy(productCount = newProductCount)
            } else {
                product
            }
        }

        val updatedState = currentState.copy(products = updatedProducts)
        _state.value = updatedState

        onUpdateProductInCart(
            productId,
            updatedProducts.find { it.productId == productId }?.productCount ?: 0
        )
    }

    private fun decrementProductCountByOne(productId: Long) {
        val currentState = _state.value

        val updatedProducts = currentState.products.map { product ->
            if (product.productId == productId) {
                val currentCount = product.productCount
                val newProductCount = if (currentCount > 1) currentCount - 1 else 1
                product.copy(productCount = newProductCount)
            } else {
                product
            }
        }
        val updatedState = currentState.copy(products = updatedProducts)
        _state.value = updatedState
        onUpdateProductInCart(
            productId,
            updatedProducts.find { it.productId == productId }?.productCount ?: 0
        )
    }

    private fun onUpdateProductInCart(productId: Long, count: Int) {
        tryToExecute(
            { cartUseCase.addToCart(productId, count) },
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
        _state.update { it.copy(isLoading = true) }
        incrementProductCountByOne(productId)
    }

    override fun onClickMinusCountProductInCart(productId: Long) {
        _state.update { it.copy(isLoading = true) }
        decrementProductCountByOne(productId)
    }

    override fun onClickViewOrders() {
        effectActionExecutor(_effect, CartUiEffect.ClickViewOrdersEffect)
    }

    override fun onClickOrderNowButton() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { checkout() },
            ::onCheckOutSuccess,
            ::onCheckOutError
        )
    }

    private fun onCheckOutSuccess(message: String) {
        _state.update {
            it.copy(
                isLoading = false,
                products = emptyList(),
                bottomSheetIsDisplayed = true
            )
        }
    }

    private fun onCheckOutError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun onClickDiscoverButton() {
        effectActionExecutor(_effect, CartUiEffect.ClickDiscoverEffect)
    }

    override fun deleteCart(position: Long) {
        _state.update { it.copy(isLoading = true) }
        val productId = state.value.products[position.toInt()].productId
        tryToExecute(
            { cartUseCase.deleteFromCart(productId) },
            ::onDeleteFromCartSuccess,
            ::onDeleteFromCartError
        )
    }

    override fun hideBottomSheet() {
        _state.update { it.copy(bottomSheetIsDisplayed = false) }
    }

    private fun onDeleteFromCartSuccess(message: String) {
        _state.update {
            it.copy(error = null)
        }
        getChosenCartProducts()
    }

    private fun onDeleteFromCartError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error, isError = true) }
    }


}