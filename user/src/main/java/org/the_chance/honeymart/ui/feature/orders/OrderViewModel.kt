package org.the_chance.honeymart.ui.feature.orders

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.OrderEntity
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.domain.usecase.UpdateOrderStateUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getAllOrders: GetAllOrdersUseCase,
    private val updateOrderStateUseCase: UpdateOrderStateUseCase,
) : BaseViewModel<OrdersUiState, OrderUiEffect>(OrdersUiState()), OrdersInteractionsListener {
    override val TAG: String = this::class.simpleName.toString()


    override fun getAllProcessingOrders() {
        _state.update {
            it.copy(isLoading = true, isError = false, orderStates = OrderStates.PROCESSING)
        }
        tryToExecute(
            { getAllOrders(OrderStates.PROCESSING.state) },
            ::onGetProcessingOrdersSuccess,
            ::onGetProcessingOrdersError
        )
    }

    private fun onGetProcessingOrdersSuccess(orders: List<OrderEntity>) {
        _state.update { it.copy(isLoading = false, orders = orders.map { it.toOrderUiState() }) }
    }

    private fun onGetProcessingOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun getAllDoneOrders() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.DONE, isError = false)
        }
        tryToExecute(
            { getAllOrders(OrderStates.DONE.state)},
            ::onGetDoneOrdersSuccess,
            ::onGetDoneOrdersError
        )

    }

    private fun onGetDoneOrdersSuccess(orders: List<OrderEntity>) {
        _state.update { it.copy(isLoading = false, orders = orders.map { it.toOrderUiState() } ) }
    }

    private fun onGetDoneOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun getAllCancelOrders() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.CANCELED, isError = false)
        }
        tryToExecute(
            { getAllOrders(OrderStates.CANCELED.state)},
            ::onGetCancelOrdersSuccess,
            ::onGetCancelOrdersError
        )
    }

    private fun onGetCancelOrdersSuccess(orders: List<OrderEntity>) {
        _state.update { it.copy(isLoading = false, orders = orders.map { it.toOrderUiState() } ) }
    }

    private fun onGetCancelOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    override fun updateOrders(position: Long, orderState: Int) {
        val orderId = state.value.orders[position.toInt()].orderId
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
            else -> Unit
        }
    }

    private fun updateOrdersError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }


    override fun onClickOrder(orderId: Long) {
        effectActionExecutor(_effect, OrderUiEffect.ClickOrderEffect(orderId))
    }

    override fun onClickDiscoverMarkets() {
        effectActionExecutor(_effect, OrderUiEffect.ClickDiscoverMarketsEffect)
    }
}