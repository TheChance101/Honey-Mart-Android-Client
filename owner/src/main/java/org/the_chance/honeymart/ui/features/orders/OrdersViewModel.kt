package org.the_chance.honeymart.ui.features.orders

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getAllOrders: GetAllOrdersUseCase,
) : BaseViewModel<OrdersUiState, OrderUiEffect>(OrdersUiState()), OrdersInteractionsListener {
    override val TAG: String = this::class.simpleName.toString()
    override fun onClickOrder(orderId: Long) {
        effectActionExecutor(_effect, OrderUiEffect.ClickOrderEffect(orderId))
    }

    override fun getAllOrders() {
        _state.update {
            it.copy(isLoading = true, isError = false, orderStates = OrderStates.ALL)
        }
        tryToExecute(
            { getAllOrders(OrderStates.ALL.state).map { it.toOrderUiState() } },
            ::onSuccess,
            ::onError
        )
    }

    override fun getAllPendingOrders() {
        _state.update {
            it.copy(isLoading = true, isError = false, orderStates = OrderStates.PENDING)
        }
        tryToExecute(
            { getAllOrders(OrderStates.PENDING.state).map { it.toOrderUiState() } },
            ::onSuccess,
            ::onError
        )
    }

    override fun getAllProcessingOrders() {
        _state.update {
            it.copy(isLoading = true, isError = false, orderStates = OrderStates.PROCESSING)
        }
        tryToExecute(
            { getAllOrders(OrderStates.PROCESSING.state).map { it.toOrderUiState() } },
            ::onSuccess,
            ::onError
        )
    }

    override fun getAllDoneOrders() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.DONE, isError = false)
        }
        tryToExecute(
            { getAllOrders(OrderStates.DONE.state).map { it.toOrderUiState() } },
            ::onSuccess,
            ::onError
        )
    }

    override fun getAllCancelOrders() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.CANCELED, isError = false)
        }
        tryToExecute(
            { getAllOrders(OrderStates.CANCELED.state).map { it.toOrderUiState() } },
            ::onSuccess,
            ::onError
        )
    }


    private fun onSuccess(orders: List<OrderUiState>) {
        _state.update { it.copy(isLoading = false, orders = orders) }
    }

    private fun onError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }
}