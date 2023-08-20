package org.the_chance.honeymart.ui.features.requests

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class RequestsUiEffect: BaseUiEffect{
    object onClickRequest: RequestsUiEffect()
}
