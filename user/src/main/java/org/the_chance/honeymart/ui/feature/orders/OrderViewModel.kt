package org.the_chance.honeymart.ui.feature.orders

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.domain.usecase.UpdateOrderStateUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.feature.ui_effect.OrderUiEffect
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.OrderStates
import org.the_chance.honeymart.ui.feature.uistate.OrderUiState
import org.the_chance.honeymart.ui.feature.uistate.OrdersUiState
import org.the_chance.honeymart.ui.feature.uistate.toOrderUiState
import org.the_chance.honeymart.util.Constant
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
                isError = false,
                orderStates = OrderStates.PROCESSING
            )
        }
        executeAction(_effect, OrderUiEffect.ClickProcessing)
        tryToExecute(
            { getAllOrders(Constant.ORDER_STATE_1).map { it.toOrderUiState() } },
            ::onGetProcessingOrdersSuccess,
            ::onGetProcessingOrdersError
        )
    }

    private fun onGetProcessingOrdersSuccess(orders: List<OrderUiState>) {
        _state.update { it.copy(isLoading = false, orders = orders) }
    }

    private fun onGetProcessingOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    fun getAllDoneOrders() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.DONE, isError = false)
        }
        executeAction(_effect, OrderUiEffect.ClickDone)
        tryToExecute(
            { getAllOrders(Constant.ORDER_STATE_2).map { it.toOrderUiState() } },
            ::onGetDoneOrdersSuccess,
            ::onGetDoneOrdersError
        )
    }

    private fun onGetDoneOrdersSuccess(orders: List<OrderUiState>) {
        _state.update { it.copy(isLoading = false, orders = orders) }
    }

    private fun onGetDoneOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    fun getAllCancelOrders() {
        _state.update {
            it.copy(
                isLoading = true,
                orderStates = OrderStates.CANCELED,
                isError = false
            )
        }
        executeAction(_effect, OrderUiEffect.ClickCanceled)
        tryToExecute(
            { getAllOrders(Constant.ORDER_STATE_3).map { it.toOrderUiState() } },
            ::onGetCancelOrdersSuccess,
            ::onGetCancelOrdersError
        )
    }

    private fun onGetCancelOrdersSuccess(orders: List<OrderUiState>) {
        _state.update { it.copy(isLoading = false, orders = orders) }
    }

    private fun onGetCancelOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    fun updateOrders(id: Long, stateOrder: Int) {
        val orderId = state.value.orders[id.toInt()].orderId
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { updateOrderStateUseCase(orderId, stateOrder) },
            ::updateOrdersSuccess,
            ::updateOrdersError
        )

    }

    private fun updateOrdersSuccess(state: Boolean) {
        _state.update { it.copy(isLoading = false, state = state) }
        when (_state.value.orderStates) {
            OrderStates.PROCESSING -> getAllProcessingOrders()
            OrderStates.DONE -> getAllDoneOrders()
            OrderStates.CANCELED -> getAllCancelOrders()
        }
    }

    private fun updateOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    fun onClickDiscoverMarketsButton() {
        executeAction(_effect, OrderUiEffect.ClickDiscoverMarketsEffect)
    }

    override fun onClickOrder(orderId: Long) {
      executeAction(_effect, OrderUiEffect.ClickOrderEffect(orderId))
    }
}
