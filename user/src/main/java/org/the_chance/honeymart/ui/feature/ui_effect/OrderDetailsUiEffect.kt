package org.the_chance.honeymart.ui.feature.ui_effect


sealed class OrderDetailsUiEffect: BaseUiEffect() {
    data class ClickProductEffect(val orderId: Long) : OrderDetailsUiEffect()
}