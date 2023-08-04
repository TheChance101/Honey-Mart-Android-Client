package org.the_chance.honeymart.ui.feature.cart

import org.the_chance.honeymart.ui.base.BaseUiEffect


sealed class CartUiEffect : BaseUiEffect {
    object ClickDiscoverEffect : CartUiEffect()
    object ClickViewOrdersEffect : CartUiEffect()
}