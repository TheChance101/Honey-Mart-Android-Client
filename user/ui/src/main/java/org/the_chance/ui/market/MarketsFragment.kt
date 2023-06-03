package org.the_chance.ui.market

import androidx.fragment.app.viewModels
import org.the_chance.ui.BaseFragment
import org.the_chance.ui.R
import org.the_chance.ui.databinding.FragmentMarketsBinding

class MarketsFragment : BaseFragment<FragmentMarketsBinding>()  {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_markets
    override val viewModel: MarketViewModel by viewModels()
}