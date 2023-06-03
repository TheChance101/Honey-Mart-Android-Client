package org.the_chance.ui.product

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.ui.BaseViewModel

@HiltViewModel
class ProductViewModel : BaseViewModel(), CategoryProductInteractionListener {
    override val TAG: String = this::class.java.simpleName


    override fun onClickCategoryProduct(id: Int) {
        TODO("Not yet implemented")
    }
}