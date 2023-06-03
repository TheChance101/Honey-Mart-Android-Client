package org.the_chance.ui.product

import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.ui.R
import org.the_chance.ui.product.uistste.ProductUiState

class CategoryProductAdapter(listener: CategoryProductInteractionListener) :
    BaseAdapter<ProductUiState>(listener) {
    override val layoutID: Int = R.layout.item_category_product
}

interface CategoryProductInteractionListener : BaseInteractionListener {
    fun onClickCategoryProduct(id: Int)
}