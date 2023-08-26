package org.the_chance.honeymart.ui.features.orders

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.OrderProductDetailsEntity
import org.the_chance.honeymart.domain.usecase.GetAllMarketOrdersUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderDetailsUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderProductsDetailsUseCase
import org.the_chance.honeymart.domain.usecase.UpdateOrderStateUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getAllMarketOrders: GetAllMarketOrdersUseCase,
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val getOrderProductDetailsUseCase: GetOrderProductsDetailsUseCase,
    private val updateOrderStateUseCase: UpdateOrderStateUseCase,
) : BaseViewModel<OrdersUiState, OrderUiEffect>(OrdersUiState()), OrdersInteractionsListener {
    override val TAG: String = this::class.simpleName.toString()

    init {
        getAllMarketOrder(OrderStates.ALL)
        updateStateOrder(_state.value.orderId, OrderStates.PROCESSING)
        resetStateScreen()
    }

    override fun getAllMarketOrder(orderState: OrderStates) {
        _state.update {
            it.copy(isLoading = true, isError = false, orderStates = orderState)
        }
        tryToExecute(
            { getAllMarketOrders(orderState.state).map { it.toOrderUiState() } },
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

    private fun getOrderDetails(orderId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getOrderDetailsUseCase(orderId) },
            ::onGetOrderDetailsSuccess,
            ::onGetOrderDetailsError
        )

        getOrderProductDetails(orderId)
    }

    private fun onGetOrderDetailsSuccess(orderDetails: OrderDetailsEntity) {
        _state.update {
            it.copy(
                isLoading = false,
                orderDetails = orderDetails.toOrderParentDetailsUiState(),
            )
        }
        updateButtonsState()
    }

    private fun onGetOrderDetailsError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = errorHandler) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    private fun getOrderProductDetails(orderId: Long) {
        tryToExecute(
            { getOrderProductDetailsUseCase(orderId) },
            ::onGetOrderProductDetailsSuccess,
            ::onGetOrderProductDetailsError
        )
    }

    private fun onGetOrderProductDetailsSuccess(products: List<OrderProductDetailsEntity>) {
        _state.update {
            it.copy(
                isLoading = false,
                products = products.toOrderDetailsProductUiState()
            )
        }
    }

    private fun onGetOrderProductDetailsError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    override fun onClickProduct(product: OrderDetailsProductUiState) {
        _state.update {
            it.copy(
                product = product,
                showState = it.showState.copy(showProductDetails = true)
            )
        }
    }

    override fun onClickOrder(orderDetails: OrderUiState, id: Long) {
        effectActionExecutor(_effect, OrderUiEffect.ClickOrderEffect(id))
        getOrderDetails(id)
        val updatedOrders = updateSelectedOrder(_state.value.orders, id)
        _state.update {
            it.copy(
                orders = updatedOrders,
                orderId = id
            )
        }
    }

    private fun updateSelectedOrder(
        orders: List<OrderUiState>,
        selectedOrderId: Long,
    ): List<OrderUiState> {
        return orders.map { order ->
            order.copy(isOrderSelected = order.orderId == selectedOrderId)
        }
    }

    override fun updateStateOrder(id: Long?, updateState: OrderStates) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { updateOrderStateUseCase(id = id, state = updateState.state) },
            ::onUpdateStateOrderSuccess,
            ::onUpdateStateOrderError
        )
        _state.update { it.copy(order = it.order.copy(orderId = id!!, states = updateState)) }
    }

    private fun onUpdateStateOrderSuccess(success: Boolean) {
        _state.update {
            it.copy(isLoading = false)
        }
        getAllMarketOrder(_state.value.orderStates)
    }

    private fun onUpdateStateOrderError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }
    }

    private fun updateButtonsState() {
        val newButtonsState = when (state.value.orderDetails.state) {
            OrderStates.PENDING.state -> ButtonsState(
                confirmText = "Approve",
                cancelText = "Decline",
                onClickConfirm = {
                    updateStateOrder(
                        id = state.value.orderId,
                        updateState = OrderStates.PROCESSING
                    )
                    getAllMarketOrder(OrderStates.PENDING)
                },
                onClickCancel = {
                    updateStateOrder(
                        state.value.orderId,
                        updateState = OrderStates.CANCELED
                    )
                    getAllMarketOrder(OrderStates.PENDING)
                }
            )

            OrderStates.PROCESSING.state -> ButtonsState(
                confirmText = "Done",
                cancelText = "Cancel",
                onClickConfirm = {
                    updateStateOrder(
                        id = state.value.orderId,
                        updateState = OrderStates.DONE
                    )

                    getAllMarketOrder(OrderStates.PROCESSING)
                },
                onClickCancel = {
                    updateStateOrder(
                        state.value.orderId,
                        updateState = OrderStates.CANCELED
                    )
                    getAllMarketOrder(OrderStates.PROCESSING)
                }
            )

            else -> return
        }
        _state.update { it.copy(orderDetails = it.orderDetails.copy(buttonsState = newButtonsState)) }
    }

    fun resetStateScreen() {
        _state.update {
            it.copy(
                showState = it.showState.copy(
                    showProductDetails = false,
                )
            )
        }
    }

}