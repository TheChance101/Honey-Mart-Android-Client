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
    private var isOrdering = false
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
        if (isOrdering) {
            _state.update { it.copy(isLoading = true) }
            tryToExecute(
                { checkout() },
                ::onCheckOutSuccess,
                ::onCheckOutError
            )
        }
    }

    private fun onGetAllCartError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
        if (error is ErrorHandler.UnAuthorizedUser){
        }
    }


    private fun updateProductCount(productId: Long, increment: Boolean) {
        val currentState = _state.value

        val updatedProducts = currentState.products.map { product ->
            if (product.productId == productId) {
                val currentCount = product.productCount
                val newProductCount = when {
                    increment && currentCount < 100 -> currentCount + 1
                    !increment && currentCount > 1 -> currentCount - 1
                    else -> currentCount
                }
                val newTotalPrice = product.productPrice * newProductCount
                product.copy(productCount = newProductCount, totalPrice = newTotalPrice)
            } else {
                product
            }
        }

        val updatedTotal = updatedProducts.sumByDouble { it.totalPrice }

        val updatedState = currentState.copy(products = updatedProducts, total = updatedTotal)
        _state.value = updatedState
        onUpdateProductInCart(productId, updatedProducts.find { it.productId == productId }?.productCount ?: 0)
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
//        getChosenCartProducts()
    }

    private fun onUpdateProductInCartError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun onClickAddCountProductInCart(productId: Long) {
        updateProductCount(productId, true)
    }

    override fun onClickMinusCountProductInCart(productId: Long) {
        updateProductCount(productId, false)
    }

    override fun onClickViewOrders() {
        effectActionExecutor(_effect, CartUiEffect.ClickViewOrdersEffect)
    }

    override fun onClickOrderNowButton() {
        isOrdering = true
        _state.update { it.copy(isLoading = true) }
        getChosenCartProducts()
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