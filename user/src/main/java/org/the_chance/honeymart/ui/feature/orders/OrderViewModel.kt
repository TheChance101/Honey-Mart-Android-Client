package org.the_chance.honeymart.ui.feature.orders

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.OrderUiState
import org.the_chance.honeymart.ui.feature.uistate.OrdersUiState
import org.the_chance.honeymart.ui.feature.uistate.toOrderUiState
import org.the_chance.honeymart.ui.feature.wishlist.WishListUiEffect
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getAllOrders: GetAllOrdersUseCase,
    savedStateHandle: SavedStateHandle) :
    BaseViewModel<OrdersUiState, OrderUiEffect>(OrdersUiState()), OrderInteractionListener {
    override val TAG: String = this::class.simpleName.toString()

    init {
//        _state.update { currentState ->
//            currentState.copy(isLoading = false, isError = false, orders = fakeOrders)
//        }
    }

    private fun getOrders() {
//        _state.update { it.copy(isLoading = true) }
//        tryToExecute(
//            { getAllOrders().map { it.toOrderUiState() } },
//            ::onOrderSuccess,
//            ::onOrderError
//        )
    }

    private fun onOrderError(throwable: Throwable) {
        _state.update { it.copy(isLoading = false, isError = true) }
    }

    private fun onOrderSuccess(orders: List<OrderUiState>) {
        _state.update {
            it.copy(isLoading = false, isError = false, orders = orders)
        }
    }

    fun onClickDiscoverMarketsButton() {
        viewModelScope.launch {
            _effect.emit(EventHandler(OrderUiEffect.ClickDiscoverMarketsEffect))
        }
    }

    override fun onClickOrder(orderId: Long) {
        TODO("Not yet implemented")
    }
}


