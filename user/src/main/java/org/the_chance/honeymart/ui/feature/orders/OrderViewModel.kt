package org.the_chance.honeymart.ui.feature.orders

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.domain.usecase.UpdateOrderStateUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.OrderStates
import org.the_chance.honeymart.ui.feature.uistate.OrderUiState
import org.the_chance.honeymart.ui.feature.uistate.OrdersUiState
import org.the_chance.honeymart.ui.feature.uistate.toOrderUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getAllOrders: GetAllOrdersUseCase,
    private val updateOrderStateUseCase: UpdateOrderStateUseCase,
) : BaseViewModel<OrdersUiState, OrderUiEffect>(OrdersUiState()), OrderInteractionListener {
    override val TAG: String = this::class.simpleName.toString()

    init {
        getAllProcessingOrders()
    }

    fun getAllProcessingOrders() {
        _state.update {
            it.copy(
                isLoading = true,
                orderStates = OrderStates.PROCESSING
            )
        }
        viewModelScope.launch {
            _effect.emit(EventHandler(OrderUiEffect.ClickProcessing))
        }
        tryToExecute(
            { getAllOrders(1).map { it.toOrderUiState() } },
            ::onGetProcessingOrdersSuccess,
            ::onGetProcessingOrdersError
        )
    }

    private fun onGetProcessingOrdersSuccess(orders: List<OrderUiState>) {
        _state.update { it.copy(isLoading = false, orders = orders) }
    }

    private fun onGetProcessingOrdersError(throwable: Exception) {
        _state.update { it.copy(isLoading = false) }
    }

    fun getAllDoneOrders(state: Int) {
        _state.update {
            it.copy(
                isLoading = true,
                orderStates = OrderStates.DONE
            )
        }
        viewModelScope.launch {
            _effect.emit(EventHandler(OrderUiEffect.ClickDone))
        }
        tryToExecute(
            { getAllOrders(state).map { it.toOrderUiState() } },
            ::onGetDoneOrdersSuccess,
            ::onGetDoneOrdersError
        )
    }

    private fun onGetDoneOrdersSuccess(orders: List<OrderUiState>) {
        _state.update { it.copy(isLoading = false, orders = orders) }
    }

    private fun onGetDoneOrdersError(throwable: Exception) {
        _state.update { it.copy(isLoading = false) }
    }

    fun getAllCancelOrders(state: Int) {
        _state.update {
            it.copy(
                isLoading = true,
                orderStates = OrderStates.CANCELED
            )
        }
        viewModelScope.launch {
            _effect.emit(EventHandler(OrderUiEffect.ClickCanceled))
        }
        tryToExecute(
            { getAllOrders(state).map { it.toOrderUiState() } },
            ::onGetCancelOrdersSuccess,
            ::onGetCancelOrdersError
        )
    }

    private fun onGetCancelOrdersSuccess(orders: List<OrderUiState>) {
        _state.update { it.copy(isLoading = false, orders = orders) }
    }

    private fun onGetCancelOrdersError(throwable: Exception) {
        _state.update { it.copy(isLoading = false) }
    }

    fun updateOrders(id: Long, stateOrder: Int) {
        val orderId = state.value.orders[id.toInt()].orderId
        _state.update {
            it.copy(
                isLoading = true,
            )
        }
        tryToExecute(
            { updateOrderStateUseCase(orderId, stateOrder) },
            ::updateOrdersSuccess,
            ::updateOrdersError
        )

    }

    private fun updateOrdersSuccess(state: Boolean) {
        _state.update { it.copy(isLoading = false, state = state) }
        getAllProcessingOrders()
    }

    private fun updateOrdersError(throwable: Exception) {
        _state.update { it.copy(isLoading = false) }
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


