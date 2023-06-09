package org.the_chance.honeymart.ui.feature.market

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.util.EventObserve
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentMarketsBinding

@AndroidEntryPoint
class MarketsFragment : BaseFragment<FragmentMarketsBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_markets
    override val viewModel: MarketViewModel by viewModels()

    override fun setup() {

        val adapter = MarketAdapter(viewModel)
        binding.recyclerMarkets.adapter = adapter
        setupScrollListenerForRecyclerView(binding.recyclerMarkets)
        observeOnMarket()
    }

    private fun observeOnMarket() {
        viewModel.uiMarketState.observe(this, EventObserve { marketId ->
            navigateToCategory(marketId)
            log(marketId)
        })
    }

    private fun navigateToCategory(marketId: Long) {
        val action = MarketsFragmentDirections
            .actionMarketsFragmentToCategoriesFragment(marketId)
        findNavController().navigate(action)
    }
}