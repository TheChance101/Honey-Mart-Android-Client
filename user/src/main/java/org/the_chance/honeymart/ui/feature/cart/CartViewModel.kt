package org.the_chance.honeymart.ui.feature.cart

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.DeleteFromCartUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCartUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CartListProductUiState
import org.the_chance.honeymart.ui.feature.uistate.CartUiState
import org.the_chance.honeymart.ui.feature.uistate.toCartListProductUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCartUseCase: GetAllCartUseCase,
    private val deleteFromCartUseCase: DeleteFromCartUseCase,
) : BaseViewModel<CartUiState, CartUiEffect>(CartUiState()),
    CartInteractionListener {
    val fakeCarts = MutableList(4) {
        CartListProductUiState(41, "Product 1", 100.0, 10)
        CartListProductUiState(31, "Product 2", 200.5, 3)
        CartListProductUiState(21, "Product 3", 300.9, 2)
        CartListProductUiState(15, "Product 4", 400.9, 5)
    }
    override val TAG: String = this::class.java.simpleName

    init {
        getChosenCartProducts()
    }

    private fun getChosenCartProducts() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllCartUseCase().map { it.toCartListProductUiState() } },
            ::onGetAllCartSuccess,
            ::onGetAllCartError
        )
    }

    private fun onGetAllCartSuccess(products: List<CartListProductUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                products = products,
            )
        }
    }

    private fun onGetAllCartError(throwable: Throwable) {
        this._state.update {
            it.copy(isLoading = false, isError = true)
        }
    }

    override fun onClickCart(productId: Long) {
    }
    fun onClickDiscoverButton() {
        viewModelScope.launch {
            _effect.emit(EventHandler(CartUiEffect.ClickDiscoverEffect))
        }
    }
    override fun onClickDeleteCart(position: Long) {
        val productId = state.value.products.indexOfFirst {it.productId == position }
        fakeCarts.removeAt(position .toInt()+1)
        log("onClickDeleteCart: $productId")
        log("onClickDeleteCart: $position")
//            viewModelScope.launch {
//            deleteFromCartUseCase(productId.toLong())
//        }
    }

}