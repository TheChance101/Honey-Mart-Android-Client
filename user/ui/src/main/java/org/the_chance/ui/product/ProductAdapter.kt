package org.the_chance.ui.product

import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.ui.R
import org.the_chance.ui.product.uistste.ProductsUiState

class ProductAdapter(listener: ProductInteractionListener) :
    BaseAdapter<ProductsUiState>(listener) {
    override val layoutID: Int = R.layout.item_products
}

interface ProductInteractionListener : BaseInteractionListener {
    fun onClickProduct(id: Int)
}