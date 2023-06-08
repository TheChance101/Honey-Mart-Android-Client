package org.the_chance.honeymart.ui.feature.market

import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.user.R

class MarketAdapter(listener: MarketInteractionListener) : BaseAdapter<MarketUiState>(listener) {
    override val layoutID: Int get() = R.layout.item_market

}

interface MarketInteractionListener: BaseInteractionListener{
    fun onClickMarket(id: Long)
}
