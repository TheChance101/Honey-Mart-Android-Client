package org.the_chance.honeymart.ui.feature.order_details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetOrderDetailsUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderProductsDetailsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val getOrderProductsDetailsUseCase: GetOrderProductsDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<OrderDetailsUiState, OrderDetailsUiEffect>(OrderDetailsUiState()),
    OrderDetailsInteractionListener {

    override val TAG: String = this::class.java.simpleName

    private val orderArgs = OrderDetailsArgs(savedStateHandle)

    init {
        getData()
    }

    private fun getData() {
        getOrderProducts()
        getOrderDetails()
    }

    private fun getOrderProducts() {
        _state.update { it.copy(isProductsLoading = true, isError = false) }
        tryToExecute(
            { getOrderProductsDetailsUseCase(orderArgs.orderId.toLong()).map { it.toOrderDetailsProductUiState() } },
            ::onGetOrderProductsSuccess,
            ::onGetOrderProductsError
        )
    }

    private fun onGetOrderProductsSuccess(products: List<OrderDetailsProductUiState>) {
        _state.update { it.copy(isProductsLoading = false, products = products) }
    }

    private fun onGetOrderProductsError(error: ErrorHandler) {
        _state.update { it.copy(isProductsLoading = false, error = error) }
        if (error is ErrorHandler.NoConnection) {
            _state.update { it.copy(isError = true) }
        }
    }

    private fun getOrderDetails() {
        _state.update { it.copy(isDetailsLoading = true, isError = false) }
        tryToExecute(
            { getOrderDetailsUseCase(orderArgs.orderId.toLong()).toOrderParentDetailsUiState() },
            ::onGetOrderDetailsSuccess,
            ::onGetOrderDetailsError
        )
    }

    private fun onGetOrderDetailsSuccess(orderDetails: OrderParentDetailsUiState) {
        _state.update { it.copy(isDetailsLoading = false, orderDetails = orderDetails) }
    }

    private fun onGetOrderDetailsError(error: ErrorHandler) {
        _state.update { it.copy(isDetailsLoading = false, error = error) }
    }

    override fun onClickOrder(productId: Long) {
        executeAction(_effect, OrderDetailsUiEffect.ClickProductEffect(productId))

    }
}