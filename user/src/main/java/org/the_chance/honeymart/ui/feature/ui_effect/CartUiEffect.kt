package org.the_chance.honeymart.ui.feature.ui_effect


sealed class CartUiEffect : BaseUiEffect(){
    object ClickOrderEffect : CartUiEffect()
    object ClickDiscoverEffect : CartUiEffect()
}