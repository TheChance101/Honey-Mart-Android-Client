package org.the_chance.honeymart.ui.feature.orders

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.the_chance.honeymart.domain.usecase.GetAllOrdersUseCase
import org.the_chance.honeymart.domain.usecase.UpdateOrderStateUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.orders.composable.OrdersInteractionsListener
import org.the_chance.honeymart.util.Constant
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getAllOrders: GetAllOrdersUseCase,
    private val updateOrderStateUseCase: UpdateOrderStateUseCase,
) : BaseViewModel<OrdersUiState, OrderUiEffect>(OrdersUiState()),
    OrdersInteractionsListener {
    override val TAG: String = this::class.simpleName.toString()
    private var job: Job? = null

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
        viewModelScope.launch {
            _effect.emit(EventHandler(OrderUiEffect.ClickProcessing))
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

    fun getAllDoneOrders() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.DONE, isError = false)
        }
        viewModelScope.launch {
            _effect.emit(EventHandler(OrderUiEffect.ClickDone))
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

    fun getAllCancelOrders() {
        _state.update {
            it.copy(
                isLoading = true,
                orderStates = OrderStates.CANCELED,
                isError = false
            )
        }

        viewModelScope.launch {
            _effect.emit(EventHandler(OrderUiEffect.ClickCanceled))
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
        viewModelScope.launch {
            _effect.emit(EventHandler(OrderUiEffect.ClickDiscoverMarketsEffect))
        }
    }

    override fun onClickOrder(orderId: Long) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(10)
            _effect.emit(EventHandler(OrderUiEffect.ClickOrderEffect(orderId)))
        }
    }

    override fun onClickProcessingOrder() {
        _state.update {
            it.copy(
                isLoading = true,
                isError = false,
                orderStates = OrderStates.PROCESSING
            )
        }
        tryToExecute(
            { getAllOrders(Constant.ORDER_STATE_1).map { it.toOrderUiState() } },
            ::onGetProcessingOrdersSuccess,
            ::onGetProcessingOrdersError
        )
    }

    override fun onClickDoneOrder() {
        _state.update {
            it.copy(isLoading = true, orderStates = OrderStates.DONE, isError = false)
        }
        tryToExecute(
            { getAllOrders(Constant.ORDER_STATE_2).map { it.toOrderUiState() } },
            ::onGetDoneOrdersSuccess,
            ::onGetDoneOrdersError
        )
    }

    override fun onClickCancelOrder() {
        _state.update {
            it.copy(
                isLoading = true,
                orderStates = OrderStates.CANCELED,
                isError = false
            )
        }
        tryToExecute(
            { getAllOrders(Constant.ORDER_STATE_3).map { it.toOrderUiState() } },
            ::onGetCancelOrdersSuccess,
            ::onGetCancelOrdersError
        )
    }

    override fun onClickConfirmOrder() {
        TODO("Not yet implemented")
    }
}
