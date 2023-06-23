package org.the_chance.honeymart.ui.feature.orders

import org.the_chance.honeymart.util.AuthData


sealed class OrderUiEffect {
    data class ClickOrderEffect(val orderId: Long) : OrderUiEffect()
    data class UnAuthorizedUserEffect(val authData:AuthData.Order) : OrderUiEffect()
    object ClickDiscoverMarketsEffect : OrderUiEffect()
    object ClickProcessing : OrderUiEffect()
    object ClickCanceled : OrderUiEffect()
    object ClickDone : OrderUiEffect()
}
