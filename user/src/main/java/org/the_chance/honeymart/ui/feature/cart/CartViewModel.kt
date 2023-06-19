package org.the_chance.honeymart.ui.feature.cart

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.DeleteFromCartUseCase
import org.the_chance.honeymart.domain.usecase.GetAllCartUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.CartListProductUiState
import org.the_chance.honeymart.ui.feature.uistate.CartUiState
import org.the_chance.honeymart.ui.feature.uistate.toCartListProductUiState
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val getAllCartUseCase: GetAllCartUseCase,
    private val deleteFromCartUseCase: DeleteFromCartUseCase,
) : BaseViewModel<CartUiState, Long>(CartUiState()),
    CartInteractionListener {

    override val TAG: String = this::class.java.simpleName

    init {
        getAllCart()
    }

    private fun getAllCart() {
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
                products = products
            )
        }
    }

    private fun onGetAllCartError(throwable: Throwable) {
        this._state.update {
            it.copy(
                isLoading = false,
                isError = true
            )
        }
    }

    override fun onClickCart(productId: Long) {
    }

    override fun onClickDeleteCart(productId: Long) {
        viewModelScope.launch {
            deleteFromCartUseCase(productId)
        }
    }

}