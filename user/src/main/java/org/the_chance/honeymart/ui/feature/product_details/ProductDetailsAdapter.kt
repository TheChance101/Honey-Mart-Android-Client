package org.the_chance.honeymart.ui.feature.product_details

import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.user.R

class ProductDetailsAdapter(listener: ProductImageInteractionListener) :
    BaseAdapter<String>(listener) {
    override val layoutID: Int get() = R.layout.item_product_image

}

interface ProductImageInteractionListener : BaseInteractionListener {
    fun onClickSmallImage(url:String)
}