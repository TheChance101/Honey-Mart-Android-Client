package org.the_chance.honeymart.ui.feature.order_details
sealed class OrderDetailsUiEffect {
    data class ClickProductEffect(val productId: Long) : OrderDetailsUiEffect()
}