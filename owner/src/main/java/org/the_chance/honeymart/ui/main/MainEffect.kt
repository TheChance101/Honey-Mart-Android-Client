package org.the_chance.honeymart.ui.main

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class MainEffect : BaseUiEffect {
    object OnClickProfileEffect : MainEffect()
    object OnClickLogoutEffect : MainEffect()
}