package org.the_chance.honeymart.ui.feature.home

import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(

) :
    BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()) {
    override val TAG: String = this::class.java.simpleName

}