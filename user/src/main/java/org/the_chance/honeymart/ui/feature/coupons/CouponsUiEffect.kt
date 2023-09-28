package org.the_chance.honeymart.ui.feature.coupons

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class CouponsUiEffect : BaseUiEffect {
    data class NavigateToProductsDetailsScreenEffect(val productId: Long) : CouponsUiEffect()
}