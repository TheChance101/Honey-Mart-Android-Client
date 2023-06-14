package org.the_chance.honeymart.ui.feature.product

import org.the_chance.honeymart.ui.base.BaseAdapter
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.user.R

class CategoryProductAdapter(listener: ProductEffect) : BaseAdapter<CategoryUiState>(listener) {
    override val layoutID: Int = R.layout.item_category_product
}