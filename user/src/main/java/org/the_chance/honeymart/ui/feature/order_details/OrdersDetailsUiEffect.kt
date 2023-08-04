package org.the_chance.honeymart.ui.feature.order_details

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class OrderDetailsUiEffect :BaseUiEffect{
    data class ClickProductEffect(val productId: Long) : OrderDetailsUiEffect()
}