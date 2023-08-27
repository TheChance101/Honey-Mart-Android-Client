package org.the_chance.honeymart.ui.features.coupons

import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

class CouponsViewModel @Inject constructor(
) : BaseViewModel<CouponsUiState, CouponsUiEffect>(CouponsUiState()), CouponsInteractionListener {

    override val TAG: String = this::class.java.simpleName

}