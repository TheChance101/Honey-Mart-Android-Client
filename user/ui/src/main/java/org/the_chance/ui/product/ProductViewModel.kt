package org.the_chance.ui.product

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.ui.BaseViewModel

@HiltViewModel
class ProductViewModel : BaseViewModel(), ProductInteractionListener ,
    CategoryProductInteractionListener {
    override val TAG: String = this::class.java.simpleName


    override fun onClickProduct(id: Int) {
        TODO("Not yet implemented")
    }

    override fun onClickCategoryProduct(id: Int) {
        TODO("Not yet implemented")
    }


}