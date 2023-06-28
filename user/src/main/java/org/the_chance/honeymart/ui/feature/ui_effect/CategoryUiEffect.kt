package org.the_chance.honeymart.ui.feature.ui_effect


sealed class CategoryUiEffect: BaseUiEffect() {
    data class OnCategoryClicked(val categoryId: Long, val marketId: Long, val position: Int): CategoryUiEffect()
}
