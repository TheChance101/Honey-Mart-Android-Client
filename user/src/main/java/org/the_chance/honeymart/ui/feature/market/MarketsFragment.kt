package org.the_chance.honeymart.ui.feature.market

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.honeymart.util.showExitAlertDialog
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentMarketsBinding

@AndroidEntryPoint
class MarketsFragment : BaseFragment<FragmentMarketsBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_markets
    override val viewModel: MarketViewModel by viewModels()

    override fun setup() {
        initiateAdapter()
        collectAction()
        setupMainFlowWindowVisibility()
        handleOnBackPressed()
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            showExitAlertDialog()
        }
    }

    private fun initiateAdapter() {
        val adapter = MarketAdapter(viewModel)
        binding.recyclerMarkets.adapter = adapter
        setupScrollListenerForRecyclerView(binding.recyclerMarkets)
    }

    private fun collectAction() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { navigateToCategory(it) }
        }
    }

    private fun navigateToCategory(marketId: Long) {
        val action = MarketsFragmentDirections.actionMarketsFragmentToCategoriesFragment(marketId)
        findNavController().navigate(action)
    }
}