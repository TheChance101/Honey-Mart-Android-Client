package org.the_chance.honeymart.ui.add_product

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(

) : BaseViewModel<AddProductUiState, Unit>(AddProductUiState()) {
    override val TAG: String = this::class.java.simpleName

}