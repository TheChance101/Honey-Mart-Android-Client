package org.the_chance.honeymart.ui.feature.signup

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.uistate.ProductsUiState
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProductsUiState, Long>(ProductsUiState()) {

    override val TAG: String = this::class.simpleName.toString()

}