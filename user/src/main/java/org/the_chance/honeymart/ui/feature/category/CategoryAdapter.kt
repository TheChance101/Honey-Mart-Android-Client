package org.the_chance.honeymart.ui.feature.category

import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.ui.category.uistate.CategoryUiState
import org.the_chance.user.R

class CategoryAdapter(
    listener: CategoryInteractionListener
) : BaseAdapter<CategoryUiState>(listener) {
    override val layoutID = R.layout.item_category
}

interface CategoryInteractionListener : BaseInteractionListener{
    fun onCategoryClicked(id: Long)
}