package org.the_chance.ui.market

import android.util.Log
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.ui.BaseFragment
import org.the_chance.ui.R
import org.the_chance.ui.databinding.FragmentMarketsBinding

@AndroidEntryPoint
class MarketsFragment : BaseFragment<FragmentMarketsBinding>() {
    override val TAG: String = "TAG"
    override val layoutIdFragment = R.layout.fragment_markets
    override val viewModel: MarketViewModel by viewModels()

    override fun setup() {
        super.setup()
        Log.e(TAG, "setup: ")
    }

}