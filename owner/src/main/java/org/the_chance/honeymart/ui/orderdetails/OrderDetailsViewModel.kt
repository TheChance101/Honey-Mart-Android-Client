package org.the_chance.honeymart.ui.orderdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.model.OrderDetailsEntity
import org.the_chance.honeymart.domain.usecase.GetOrderDetailsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<OrderDetailsUiState, OrderDetailsUiEffect>(OrderDetailsUiState()){

    override val TAG: String
        get() = this::class.simpleName.toString()


    init {
        getData()
    }

    private fun getData() {
        getOrderDetails(2)
    }

    private fun getOrderDetails(orderId: Long) {
        _state.update { it.copy(isLoading = true, isError = false) }
        tryToExecute(
            { getOrderDetailsUseCase(orderId) },
            ::onGetOrderDetailsSuccess,
            ::onGetOrderDetailsError
        )
    }

    private fun onGetOrderDetailsSuccess(orderDetails: OrderDetailsEntity) {
        _state.update { it.copy(isLoading = false,
            orderDetails = orderDetails.toOrderParentDetailsUiState()) }
        Log.e("sara",_state.value.orderDetails.product.toString())
    }

    private fun onGetOrderDetailsError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }



}