package org.the_chance.honeymart.ui.feature.cart


sealed class CartUiEffect {
    object ClickOrderEffect : CartUiEffect()
    object ClickDiscoverEffect : CartUiEffect()
}