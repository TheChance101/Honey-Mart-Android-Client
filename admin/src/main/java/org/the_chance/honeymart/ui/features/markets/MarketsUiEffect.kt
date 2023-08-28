package org.the_chance.honeymart.ui.features.markets

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class MarketsUiEffect: BaseUiEffect{
    object onClickMarket: MarketsUiEffect()
    object UnAuthorizedUserEffect : MarketsUiEffect()
    object OnClickLogoutEffect : MarketsUiEffect()

}
