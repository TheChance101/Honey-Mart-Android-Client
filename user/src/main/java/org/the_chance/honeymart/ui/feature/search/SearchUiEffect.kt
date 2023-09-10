package org.the_chance.honeymart.ui.feature.search

import org.the_chance.honeymart.ui.base.BaseUiEffect

sealed interface SearchUiEffect: BaseUiEffect{
    data class OnClickProductCard(val productId: Long): SearchUiEffect

}