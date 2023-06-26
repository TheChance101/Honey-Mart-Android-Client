package org.the_chance.honeymart.ui.feature.orderdetails

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentOrderDetailsBinding

@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {

    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_order_details
    override val viewModel: OrderDetailsViewModel by viewModels()
    private val orderDetailsAdapter: OrderDetailsAdapter by lazy { OrderDetailsAdapter(viewModel) }


    override fun setup() {
        setupMainFlowWindowVisibility()
        initAdapters()
        collectEffect()
    }

    private fun initAdapters() {
        binding.recyclerOrderDetails.adapter = orderDetailsAdapter
    }

    private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let {
                onEffect(it)
            }
        }
    }

    private fun onEffect(effect: OrderDetailsUiEffect) {
        when (effect) {
            is OrderDetailsUiEffect.ClickProductEffect ->navigateToProductsDetails(effect.orderId)
        }
    }

    private fun navigateToProductsDetails(orderId: Long) {
        val action = OrderDetailsFragmentDirections.actionGlobalProductDetailsFragment(orderId)
        findNavController().navigate(action)
    }
}