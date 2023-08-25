package org.the_chance.honeymart.ui.feature.new_products

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class RecentProductUiEffect : BaseUiEffect {
    data class ClickProductEffect(val productId: Long) : RecentProductUiEffect()
}