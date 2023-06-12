package org.the_chance.honeymart.ui.feature.product

import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.user.R

class CategoryProductAdapter(listener: CategoryProductInteractionListener) :
    BaseAdapter<CategoryUiState>(listener) {
    override val layoutID: Int = R.layout.item_category_product
}

interface CategoryProductInteractionListener : BaseInteractionListener {
    fun onClickCategoryProduct(CategoryId: Long)
}