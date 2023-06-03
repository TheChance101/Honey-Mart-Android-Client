package org.the_chance.ui.product

import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.ui.R

class ProductAdapter(listener: ProductInteractionListener) : BaseAdapter<ProductDto>(listener){
    override val layoutID: Int = R.layout.item_category_product
}

interface ProductInteractionListener : BaseInteractionListener {
    fun onClickProduct(id: Int)
}