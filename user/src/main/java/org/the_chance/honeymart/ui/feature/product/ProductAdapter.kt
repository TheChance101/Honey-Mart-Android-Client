package org.the_chance.honeymart.ui.feature.product

import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.user.R

class ProductAdapter(listener: ProductInteractionListener) : BaseAdapter<ProductUiState>(listener) {
    override val layoutID: Int = R.layout.item_products
}

interface ProductInteractionListener : BaseInteractionListener {
    fun onClickProduct(productId: Long)
    fun onClickFavIcon(productId: Long)
}