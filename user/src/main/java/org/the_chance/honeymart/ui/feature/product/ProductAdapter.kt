package org.the_chance.honeymart.ui.feature.product

import org.the_chance.honeymart.ui.base.BaseAdapter
import org.the_chance.honeymart.ui.feature.uistate.ProductUiState
import org.the_chance.user.R

class ProductAdapter(listener: ProductEffect) : BaseAdapter<ProductUiState>(listener) {
    override val layoutID: Int = R.layout.item_products
}