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
        getAllMarketOrders(OrderStates.ALL)
        updateStateOrder(_state.value.orderId, OrderStates.PROCESSING)
    }


    override fun getAllMarketOrders(orderState: OrderStates) {
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
            it.copy(
                isLoading = false,

                )
        }
    }

    private fun onUpdateStateOrderError(errorHandler: ErrorHandler) {
        _state.update { it.copy(isLoading = false) }
        if (errorHandler is ErrorHandler.NoConnection) {
            _state.update { it.copy(isLoading = false, isError = true) }
        }

    }

}