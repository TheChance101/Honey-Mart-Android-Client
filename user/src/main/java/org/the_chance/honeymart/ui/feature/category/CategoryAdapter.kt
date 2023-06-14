package org.the_chance.honeymart.ui.feature.category

import org.the_chance.honeymart.ui.base.BaseAdapter
import org.the_chance.honeymart.ui.feature.uistate.CategoryUiState
import org.the_chance.user.R

class CategoryAdapter(val listener: CategoryEffect) : BaseAdapter<CategoryUiState>(listener) {
    override val layoutID = R.layout.item_category
}
