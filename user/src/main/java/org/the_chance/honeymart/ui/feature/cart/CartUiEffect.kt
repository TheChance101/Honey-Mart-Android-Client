package org.the_chance.honeymart.ui.feature.cart


sealed class CartUiEffect {
    data class ClickOrderEffect(val orderId: Long) : CartUiEffect()
    object ClickDiscoverEffect : CartUiEffect()
}