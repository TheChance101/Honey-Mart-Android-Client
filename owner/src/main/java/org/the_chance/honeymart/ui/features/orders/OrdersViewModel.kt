package org.the_chance.honeymart.ui.features.orders

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.model.OrderProductDetailsEntity
import org.the_chance.honeymart.domain.usecase.GetAllMarketOrdersUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderDetailsUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderProductsDetailsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getAllMarketOrders: GetAllMarketOrdersUseCase,
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val getOrderProductDetailsUseCase: GetOrderProductsDetailsUseCase
) : BaseViewModel<OrdersUiState, OrderUiEffect>(OrdersUiState()), OrdersInteractionsListener {
    override val TAG: String = this::class.simpleName.toString()

    init {
        getAllMarketOrders(OrderStates.ALL)
    }

    override fun onClickOrder(id: Long) {
        effectActionExecutor(_effect, OrderUiEffect.ClickOrderEffect(id))
        val updatedOrders = updateSelectedOrder(_state.value.orders, id)
        _state.update {
            it.copy(
                orders = updatedOrders
            )
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
                showState = it.showState.copy(showProductDetails = true)
            )
        }
    }

    private fun onGetOrderDetailsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    private fun getOrderProductDetails(orderId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
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

    private fun onGetOrderProductDetailsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }
}