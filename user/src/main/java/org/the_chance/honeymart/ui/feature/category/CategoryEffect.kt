package org.the_chance.honeymart.ui.feature.category

import org.the_chance.honeymart.ui.base.BaseInteractionListener

interface CategoryEffect : BaseInteractionListener {
    fun onClickCategory(categoryId: Long)
    fun onGetAllCategories()
}