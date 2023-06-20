package org.the_chance.honeymart.ui.feature.orders


sealed class OrderUiEffect {
    object UnAuthorizedUserEffect : OrderUiEffect()
    object ClickDiscoverMarketsEffect : OrderUiEffect()
}
