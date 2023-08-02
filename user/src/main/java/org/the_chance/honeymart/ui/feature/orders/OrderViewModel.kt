package org.the_chance.honeymart.ui.feature.orders

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.domain.usecase.UpdateOrderStateUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.orders.composable.OrdersInteractionsListener
import org.the_chance.honeymart.util.Constant
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getAllOrders: GetAllOrdersUseCase,
    private val updateOrderStateUseCase: UpdateOrderStateUseCase,
) : BaseViewModel<OrdersUiState, Unit>(OrdersUiState()), OrdersInteractionsListener {
    override val TAG: String = this::class.simpleName.toString()

    private fun getAllProcessingOrders() {
        _state.update {
            it.copy(
                isLoading = true,
                isError = false,
                orderStates = OrderStates.PROCESSING,
            )
        }
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

    private fun getAllDoneOrders() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.DONE, isError = false)
        }
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

    private fun getAllCancelOrders() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.CANCELED, isError = false)
        }
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

    private fun updateOrders(id: Long, orderState: Int) {
        val orderId = state.value.orders[id.toInt()].orderId
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { updateOrderStateUseCase(orderId, orderState) },
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
            else -> {}
        }
    }

    private fun updateOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun onClickProcessingOrder() {
        _state.update {
            it.copy(isLoading = true, isError = false, orderStates = OrderStates.PROCESSING)
        }
        tryToExecute(
            { getAllOrders(OrderStates.PROCESSING.state).map { it.toOrderUiState() } },
            ::onGetProcessingOrdersSuccess,
            ::onGetProcessingOrdersError
        )
    }

    override fun onClickDoneOrder() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.DONE, isError = false)
        }
        tryToExecute(
            { getAllOrders(OrderStates.DONE.state).map { it.toOrderUiState() } },
            ::onGetDoneOrdersSuccess,
            ::onGetDoneOrdersError
        )
    }

    override fun onClickCancelOrder() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.CANCELED, isError = false)
        }
        tryToExecute(
            { getAllOrders(OrderStates.CANCELED.state).map { it.toOrderUiState() } },
            ::onGetCancelOrdersSuccess,
            ::onGetCancelOrdersError
        )
    }

    override fun onClickConfirmOrder(position: Long, orderState: Int) {
        updateOrders(position, orderState)
    }
}
