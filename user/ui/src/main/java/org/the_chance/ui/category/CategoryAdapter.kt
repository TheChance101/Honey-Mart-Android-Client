package org.the_chance.ui.category

import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.ui.R

class CategoryAdapter(
    listener: CategoryInteractionListener
) : BaseAdapter<CategoryUiState>(listener) {
    override val layoutID = R.layout.item_category
}

interface CategoryInteractionListener : BaseInteractionListener{
    fun onCategoryClicked(id: Long)
}