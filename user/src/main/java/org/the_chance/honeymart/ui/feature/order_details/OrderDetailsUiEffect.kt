package org.the_chance.honeymart.ui.feature.order_details

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed interface OrderDetailsUiEffect :BaseUiEffect{
    data class ClickProductEffect(val productId: Long) : OrderDetailsUiEffect
    data class ShowToastEffect(val message: String) : OrderDetailsUiEffect
}