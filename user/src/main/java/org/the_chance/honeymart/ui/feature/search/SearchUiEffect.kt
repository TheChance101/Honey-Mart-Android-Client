package org.the_chance.honeymart.ui.feature.search

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed class SearchUiEffect: BaseUiEffect{
    data class OnClickProductCard(val productId: Long): SearchUiEffect()

}
