package org.the_chance.honeymart.ui.feature.ui_effect


sealed class OrderUiEffect : BaseUiEffect(){
    data class ClickOrderEffect(val orderId: Long) : OrderUiEffect()
    object UnAuthorizedUserEffect : OrderUiEffect()
    object ClickDiscoverMarketsEffect : OrderUiEffect()
    object ClickProcessing : OrderUiEffect()
    object ClickCanceled : OrderUiEffect()
    object ClickDone : OrderUiEffect()
}
