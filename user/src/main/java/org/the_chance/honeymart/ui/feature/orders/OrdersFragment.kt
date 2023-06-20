package org.the_chance.honeymart.ui.feature.orders

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.uistate.OrdersUiState
import org.the_chance.honeymart.ui.feature.wishlist.WishListUiEffect
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentOrdersBinding

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_orders
    override val viewModel: OrderViewModel by viewModels()
    private val ordersAdapter: OrdersAdapter by lazy { OrdersAdapter(viewModel) }

    override fun setup() {
        initAdapter()
        handleOnBackPressed()
        collectEffect()
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.marketsFragment)
        }
    }

    private fun initAdapter() {
        binding.recyclerOrder.adapter = ordersAdapter
    }

    private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let {
                onEffect(it)
            }
        }
    }

    private fun onEffect(effect: OrderUiEffect) {
        when(effect){
            OrderUiEffect.UnAuthorizedUserEffect -> TODO()
//            is OrderUiEffect.UnAuthorizedUserEffect -> navigateToLogin()
            is OrderUiEffect.ClickDiscoverMarketsEffect -> navigateToMarkets()
        }
    }

    private fun navigateToMarkets() {
        val action = OrdersFragmentDirections.actionOrdersFragmentToMarketsFragment()
        findNavController().navigate(action)
    }

}
