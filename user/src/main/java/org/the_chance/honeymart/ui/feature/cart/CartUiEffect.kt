package org.the_chance.honeymart.ui.feature.cart

import org.the_chance.honeymart.ui.base.BaseUiEffect


sealed interface CartUiEffect : BaseUiEffect {
    object ClickDiscoverEffect : CartUiEffect
    object ClickViewOrdersEffect : CartUiEffect
}