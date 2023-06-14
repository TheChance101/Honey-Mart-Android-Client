package org.the_chance.honeymart.ui.feature.market

import org.the_chance.honeymart.ui.base.BaseAdapter
import org.the_chance.honeymart.ui.feature.uistate.MarketUiState
import org.the_chance.user.R

class MarketAdapter(listener: MarketUiEffect) : BaseAdapter<MarketUiState>(listener) {
    override val layoutID: Int get() = R.layout.item_market
}
