package org.the_chance.honeymart.ui.feature.order_details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.OrderDetails
import org.the_chance.honeymart.domain.usecase.user.UserOrderDetailsManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderDetails: UserOrderDetailsManagerUseCase,
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
            { orderDetails.getOrderProductDetailsUseCase(orderArgs.orderId.toLong()) },
            ::onGetOrderProductsSuccess,
            ::onGetOrderProductsError
        )
    }

    private fun onGetOrderProductsSuccess(products: List<OrderDetails.ProductDetails>) {
        _state.update { orderDetailsUiState ->
            orderDetailsUiState.copy(
                isProductsLoading = false,
                products = products.map { it.toOrderDetailsProductUiState() })
        }
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
            { orderDetails.getOrderDetailsUseCase(orderArgs.orderId.toLong()) },
            ::onGetOrderDetailsSuccess,
            ::onGetOrderDetailsError
        )
    }

    private fun onGetOrderDetailsSuccess(orderDetails: OrderDetails) {
        _state.update {
            it.copy(
                isDetailsLoading = false,
                orderDetails = orderDetails.toOrderParentDetailsUiState()
            )
        }
    }

    private fun onGetOrderDetailsError(error: ErrorHandler) {
        _state.update { it.copy(isDetailsLoading = false, error = error) }
    }

    override fun onClickOrder(productId: Long) {
        effectActionExecutor(_effect, OrderDetailsUiEffect.ClickProductEffect(productId))

    }
}