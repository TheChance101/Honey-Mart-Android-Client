package org.the_chance.honeymart.ui.feature.orderdetails

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.GetOrderDetailsUseCase
import org.the_chance.honeymart.domain.usecase.GetOrderProductsDetailsUseCase
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.OrderDetailsProductUiState
import org.the_chance.honeymart.ui.feature.uistate.OrderDetailsUiState
import org.the_chance.honeymart.ui.feature.uistate.toOrderDetailsProductUiState
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val getOrderProductsDetailsUseCase: GetOrderProductsDetailsUseCase
) : BaseViewModel<OrderDetailsUiState, Long>(OrderDetailsUiState()),
    OrderDetailsInteractionListener {
    override val TAG: String = this::class.java.simpleName

    private fun getOrderProducts() {

        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getOrderProductsDetailsUseCase(1).map { it.toOrderDetailsProductUiState() } },
            :: onGetOrderProductsSuccess,
            :: onGetOrderProductsError
        )
    }

    private fun onGetOrderProductsError(error: Exception) {
        this._state.update {
            it.copy(
                isLoading = false,
                isError = true,
            )
        }
    }

    private fun  onGetOrderProductsSuccess(products:List<OrderDetailsProductUiState>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                products=products
            )
        }
    }

    override fun onClickProduct(productId: Long) {
        TODO("Not yet implemented")
    }


}