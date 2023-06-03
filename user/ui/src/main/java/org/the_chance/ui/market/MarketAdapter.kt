package org.the_chance.ui.market

import org.the_chance.ui.BaseAdapter
import org.the_chance.ui.BaseInteractionListener
import org.the_chance.ui.R

class MarketAdapter(private val listener: MarketInteractionListener) {
    //override
    val layoutID: Int
        get() = R.layout.item_market
}

interface MarketInteractionListener: BaseInteractionListener{

}
