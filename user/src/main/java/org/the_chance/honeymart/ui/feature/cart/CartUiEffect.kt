package org.the_chance.honeymart.ui.feature.cart


sealed class CartUiEffect {
    object ClickDiscoverEffect : CartUiEffect()
    object ClickViewOrdersEffect : CartUiEffect()
}