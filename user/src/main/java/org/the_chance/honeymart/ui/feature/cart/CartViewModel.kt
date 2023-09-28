package org.the_chance.honeymart.ui.feature.cart


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.Cart
import org.the_chance.honeymart.domain.usecase.usecaseManager.user.CartProductsManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartProductsManagerUseCase: CartProductsManagerUseCase
) : BaseViewModel<CartUiState, CartUiEffect>(CartUiState()),
    CartInteractionListener {
    override val TAG: String = this::class.java.simpleName

    override fun getChosenCartProducts() {
        _state.update { it.copy(isLoading = true, isError = false, bottomSheetIsDisplayed = false) }
        tryToExecute(
            { cartProductsManagerUseCase.cartUseCase.getCart() },
            ::onGetAllCartSuccess,
            ::onGetAllCartError
        )
    }

    private fun onGetAllCartSuccess(cart: Cart) {
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

    }


    private fun updateProductCount(productId: Long, increment: Boolean) {
        val currentState = _state.value
        val updatedProducts = updatedProducts(currentState, productId, increment)
        val updatedTotal = updatedProducts.sumOf { it.totalPrice }
        val updatedState =
            currentState.copy(products = updatedProducts, total = updatedTotal, isLoading = true)
        _state.value = updatedState
        onUpdateProductInCart(
            productId,
            updatedProducts.find { it.productId == productId }?.productCount ?: 0
        )
    }

    private fun updatedProducts(
        currentState: CartUiState,
        productId: Long,
        increment: Boolean
    ): List<CartListProductUiState> {
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
        return updatedProducts
    }

    private fun onUpdateProductInCart(productId: Long, count: Int) {
        tryToExecute(
            { cartProductsManagerUseCase.cartUseCase.addToCart(productId, count) },
            ::onUpdateProductInCartSuccess,
            ::onUpdateProductInCartError
        )
    }

    private fun onUpdateProductInCartSuccess(message: String) {
        _state.update {
            it.copy(isLoading = false, error = null)
        }
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

    override fun onClickOrderNowButton() { checkOut() }

    private fun checkOut() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { cartProductsManagerUseCase.checkout() },
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
            { cartProductsManagerUseCase.cartUseCase.deleteFromCart(productId) },
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