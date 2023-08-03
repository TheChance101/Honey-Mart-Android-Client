package org.the_chance.honeymart.ui.feature.category


sealed class CategoryUiEffect {
    data class ClickCategoryEffect(val categoryId: Long, val marketId: Long, val position: Int)
        :CategoryUiEffect()
}