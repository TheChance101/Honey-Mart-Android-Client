package org.the_chance.honeymart.ui.features.orders

import arrow.optics.copy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.OrderDetails
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerOrdersManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.profile.ProfileUiState
import org.the_chance.honeymart.ui.features.profile.isError
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val ownerOrders: OwnerOrdersManagerUseCase
) : BaseViewModel<OrdersUiState, OrderUiEffect>(OrdersUiState()), OrdersInteractionsListener {
    override val TAG: String = this::class.simpleName.toString()

    init {
        getAllMarketOrder(OrderStates.ALL)
        resetStateScreen()
    }

    override fun getAllMarketOrder(orderState: OrderStates) {
        _state.update { state ->
            state.copy {
                OrdersUiState.isLoading set true
                OrdersUiState.isError set false
                OrdersUiState.states set orderState
                OrdersUiState.showState.showOrderDetails set false
            }
        }
        tryToExecute(
            { ownerOrders.getAllMarketOrders(orderState.state).map { it.toOrderUiState() } },
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
            _state.update { it.copy { OrdersUiState.isError set true } }
        }
    }

    private fun getOrderDetails(orderId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { ownerOrders.getOrderDetailsUseCase(orderId) },
            ::onGetOrderDetailsSuccess,
            ::onGetOrderDetailsError
        )

        getOrderProductDetails(orderId)
    }

    private fun onGetOrderDetailsSuccess(orderDetails: OrderDetails) {
        _state.update { state ->
            state.copy {
                OrdersUiState.isLoading set false
                OrdersUiState.orderDetails set orderDetails.toOrderParentDetailsUiState()
                OrdersUiState.orderStates set orderDetails.state
            }
        }
        updateButtonsState()
    }

    private fun onGetOrderDetailsError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = errorHandler) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy { OrdersUiState.isError set true } }
        }
    }

    private fun getOrderProductDetails(orderId: Long) {
        tryToExecute(
            { ownerOrders.getOrderProductDetailsUseCase(orderId) },
            ::onGetOrderProductDetailsSuccess,
            ::onGetOrderProductDetailsError
        )
    }

    private fun onGetOrderProductDetailsSuccess(products: List<OrderDetails.ProductDetails>) {
        _state.update { state ->
            state.copy {
                OrdersUiState.isLoading set false
                OrdersUiState.products set products.toOrderDetailsProductUiState()
            }
        }
    }

    private fun onGetOrderProductDetailsError(errorHandler: ErrorHandler) {
        _state.update { it.copy { OrdersUiState.isLoading set true } }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy { OrdersUiState.isError set true } }
        }
    }

    override fun onClickProduct(product: OrderDetailsProductUiState) {
        _state.update { state ->
            state.copy {
                OrdersUiState.product set product
                OrdersUiState.showState.showProductDetails set true
                OrdersUiState.showState.showOrderDetails set false
            }
        }
    }

    override fun onClickOrder(orderDetails: OrderUiState, id: Long) {
        effectActionExecutor(_effect, OrderUiEffect.ClickOrderEffect(id))
        val updatedOrders = updateSelectedOrder(_state.value.orders, id)
        _state.update { state ->
            state.copy {
                OrdersUiState.orders set updatedOrders
                OrdersUiState.orderId set id
                OrdersUiState.showState.showOrderDetails set true
            }
        }
        getOrderDetails(id)
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
            { ownerOrders.updateOrderStateUseCase(id = id, state = updateState.state) },
            ::onUpdateStateOrderSuccess,
            ::onUpdateStateOrderError
        )
    }

    private fun onUpdateStateOrderSuccess(success: Boolean) {
        _state.update { it.copy { OrdersUiState.isLoading set false } }
        getAllMarketOrder(_state.value.states)
    }

    private fun onUpdateStateOrderError(errorHandler: ErrorHandler) {
        _state.update { it.copy { OrdersUiState.isLoading set true } }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy { OrdersUiState.isError set true } }
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
        _state.update { it.copy { OrdersUiState.orderDetails.buttonsState set newButtonsState } }
    }

    fun resetStateScreen() {
        _state.update { state ->
            state.copy {
                OrdersUiState.showState.showProductDetails set false
                OrdersUiState.showState.showOrderDetails set false
            }
        }
    }
}