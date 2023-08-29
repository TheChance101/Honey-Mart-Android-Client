package org.the_chance.honeymart.ui.feature.category

import org.the_chance.honeymart.ui.base.BaseUiEffect


sealed interface CategoryUiEffect : BaseUiEffect {
    data class ClickCategoryEffect(val categoryId: Long, val marketId: Long, val position: Int) :
        CategoryUiEffect
}