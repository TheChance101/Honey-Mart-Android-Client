package org.the_chance.honeymart.ui.feature.wishlist

import org.the_chance.honeymart.ui.feature.uistate.WishListProductUiState
import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.user.R

class WishListAdapter(listener: WishListInteractionListener) :
    BaseAdapter<WishListProductUiState>(listener) {
    override val layoutID: Int = R.layout.item_wish_list
}

interface WishListInteractionListener : BaseInteractionListener {
    fun onClickProduct(productId: Long)
    fun onClickFavoriteIcon(productId: Long)
}