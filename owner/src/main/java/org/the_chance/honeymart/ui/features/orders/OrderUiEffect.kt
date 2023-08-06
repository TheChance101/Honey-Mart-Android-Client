package org.the_chance.honeymart.ui.features.orders

sealed class OrderUiEffect{
    data class ClickOrderEffect(val orderId: Long) : OrderUiEffect()
}