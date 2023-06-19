package org.the_chance.honeymart.ui.feature.orders

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.OrderUiState
import org.the_chance.honeymart.ui.feature.uistate.OrdersUiState
import org.the_chance.honeymart.ui.feature.uistate.toOrderUiState
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getAllOrders: GetAllOrdersUseCase,
    savedStateHandle: SavedStateHandle) :
    BaseViewModel<OrdersUiState, Long>(OrdersUiState()), OrderInteractionListener
{
    override val TAG: String = this::class.simpleName.toString()

    init {
        getOrderById()
    }

    private fun getOrderById(){
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getAllOrders().map { it.toOrderUiState() } },
            ::onOrderSuccess,
            ::onOrderError
        )
    }

    private fun onOrderError(throwable: Throwable) {
        _state.update { it.copy(isLoading = false, isError = true) }
    }

    private fun onOrderSuccess(orders: List<OrderUiState>){
        _state.update {
            it.copy(isLoading = false, isError = false, orders = orders)
        }
    }

    override fun onClickOrder(orderId: Long) {
        TODO("Not yet implemented")
    }


}

