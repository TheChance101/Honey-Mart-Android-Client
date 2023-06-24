package org.the_chance.honeymart.ui.feature.orderdetails


sealed class OrderDetailsUiEffect {
    data class ClickProductEffect(val orderId: Long) : OrderDetailsUiEffect()
}