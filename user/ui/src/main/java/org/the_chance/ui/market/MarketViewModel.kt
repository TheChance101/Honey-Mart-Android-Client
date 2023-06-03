package org.the_chance.ui.market

import dagger.hilt.android.lifecycle.HiltViewModel
import org.the_chance.ui.BaseViewModel

@HiltViewModel
class MarketViewModel : BaseViewModel() {
    override val TAG: String = this::class.java.simpleName
}