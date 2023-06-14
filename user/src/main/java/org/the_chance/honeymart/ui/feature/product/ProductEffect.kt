package org.the_chance.honeymart.ui.feature.product

import org.the_chance.honeymart.ui.base.BaseInteractionListener

interface ProductEffect : BaseInteractionListener {
    fun onClickProduct(productId: Long)
    fun onClickCategory(categoryId: Long)
    fun onGetAllCategories()
    fun onGetAllProducts(categoryId: Long)
}