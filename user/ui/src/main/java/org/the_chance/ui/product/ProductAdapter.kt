package org.the_chance.ui.product

import com.example.ui.R
import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener

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