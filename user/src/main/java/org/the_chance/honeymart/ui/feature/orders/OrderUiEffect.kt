package org.the_chance.honeymart.ui.feature.orders

import org.the_chance.honeymart.ui.base.BaseUiEffect


sealed class OrderUiEffect:BaseUiEffect {
    data class ClickOrderEffect(val orderId: Long) : OrderUiEffect()
    object ClickDiscoverMarketsEffect : OrderUiEffect()
}