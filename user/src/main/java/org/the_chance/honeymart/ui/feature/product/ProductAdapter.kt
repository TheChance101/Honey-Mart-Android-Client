package org.the_chance.honeymart.ui.feature.product

import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.user.R

class ProductAdapter(listener: ProductInteractionListener) : BaseAdapter<ProductDto>(listener) {
    override val layoutID: Int = R.layout.item_products
}

interface ProductInteractionListener : BaseInteractionListener {
    fun onClickProduct(id: Int)
}

data class ProductDto(
    val id: Int,
    val name: String,
    val price: String,
    val image: String,
    val category: String,
)