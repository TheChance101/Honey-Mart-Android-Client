package org.the_chance.honeymart.ui.feature.product

import org.the_chance.ui.BaseInteractionListener

interface ProductInteractionListener : BaseInteractionListener {
    fun onClickProduct(productId: Long)
    fun onClickFavIcon(productId: Long)
    fun onClickCategory(categoryId: Long)
}