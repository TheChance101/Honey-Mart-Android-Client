package org.the_chance.ui.product

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.ui.BaseViewModel

@HiltViewModel
class ProductViewModel : BaseViewModel() {
    override val TAG: String = this::class.java.simpleName
}