package org.the_chance.honeymart.ui.feature.orders

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<OrdersUiState, Long>(OrdersUiState()), OrderInteractionListener, {
    override val TAG: String = this::class.simpleName.toString()

    override fun onClickOrder(orderId: Long) {
        TODO("Not yet implemented")
    }

}

