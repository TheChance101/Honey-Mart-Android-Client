package org.the_chance.honeymart.ui.feature.orderdetails

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetOrderDetailsUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderProductsDetailsUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.OrderDetailsProductUiState
import org.the_chance.honeymart.ui.feature.uistate.OrderDetailsUiState
import org.the_chance.honeymart.ui.feature.uistate.OrderParentDetailsUiState
import org.the_chance.honeymart.ui.feature.uistate.toOrderDetailsProductUiState
import org.the_chance.honeymart.ui.feature.uistate.toOrderParentDetailsUiState
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val getOrderProductsDetailsUseCase: GetOrderProductsDetailsUseCase
) : BaseViewModel<OrderDetailsUiState, Long>(OrderDetailsUiState()),
    OrderDetailsInteractionListener {
    override val TAG: String = this::class.java.simpleName


    init {
        getOrderProducts()
        getOrderDetails()
    }


    private fun getOrderProducts() {
        _state.update { it.copy(isProductsLoading = true) }
        tryToExecute(
            { getOrderProductsDetailsUseCase(1).map { it.toOrderDetailsProductUiState() } },
            ::onGetOrderProductsSuccess,
            ::onGetOrderProductsError
        )
    }

    private fun onGetOrderProductsSuccess(products: List<OrderDetailsProductUiState>) {
        _state.update { it.copy(isProductsLoading = false, products = products) }
    }

    private fun onGetOrderProductsError(error: Exception) {
        _state.update { it.copy(isProductsLoading = false) }


    }

    private fun getOrderDetails() {
        _state.update { it.copy(isDetailsLoading = true) }
        tryToExecute(
            { getOrderDetailsUseCase(1).toOrderParentDetailsUiState() },
            ::onGetOrderDetailsSuccess,
            ::onGetOrderDetailsError
        )
    }

    private fun onGetOrderDetailsSuccess(orderDetails: OrderParentDetailsUiState) {
        _state.update { it.copy(isDetailsLoading = false, orderDetails = orderDetails) }
    }

    private fun onGetOrderDetailsError(error: Exception) {
        _state.update { it.copy(isDetailsLoading = false) }
    }


    override fun onClickProduct(productId: Long) {}


}