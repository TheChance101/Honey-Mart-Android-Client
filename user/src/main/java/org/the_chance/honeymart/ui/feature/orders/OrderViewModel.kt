package org.the_chance.honeymart.ui.feature.orders

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.domain.usecase.GetDoneOrdersUseCase
import org.the_chance.honeymart.domain.usecase.GetProcessingOrdersUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.OrderUiState
import org.the_chance.honeymart.ui.feature.uistate.OrdersUiState
import org.the_chance.honeymart.ui.feature.uistate.toOrderUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
//    private val getAllOrders: GetAllOrdersUseCase,
    private val getProcessingOrders : GetProcessingOrdersUseCase,
    private val getDoneOrders : GetDoneOrdersUseCase,
) : BaseViewModel<OrdersUiState, OrderUiEffect>(OrdersUiState()), OrderInteractionListener {
    override val TAG: String = this::class.simpleName.toString()

    init {
        getAllProcessingOrders()
    }

    fun getAllProcessingOrders() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getProcessingOrders().map { it.toOrderUiState() } },
            ::onGetProcessingOrdersSuccess,
            ::onGetProcessingOrdersError
        )
    }

    private fun onGetProcessingOrdersSuccess(orders: List<OrderUiState>) {
        _state.update { it.copy(isLoading = false, orders = orders) }
    }

    private fun onGetProcessingOrdersError(throwable: Throwable) {
        _state.update { it.copy(isLoading = false) }
    }

    fun getAllDoneOrders() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getDoneOrders().map { it.toOrderUiState() } },
            ::onGetDoneOrdersSuccess,
            ::onGetDoneOrdersError
        )
    }

    private fun onGetDoneOrdersSuccess(orders: List<OrderUiState>) {
        _state.update { it.copy(isLoading = false, orders = orders) }
    }

    private fun onGetDoneOrdersError(throwable: Throwable) {
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


