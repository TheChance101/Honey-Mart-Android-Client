package org.the_chance.honeymart.ui.feature.orders

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.uistate.OrderUiState
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
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.marketsFragment)
        }
    }

    private fun initAdapter() {
//        val fakeOrders = listOf(
//            OrderUiState(1, "Market 1", "10", 9.99),
//            OrderUiState(2, "Market 2", "5", 4.99),
//            OrderUiState(3, "Market 3", "3", 2.99)
//        )
        binding.recyclerOrder.adapter = ordersAdapter
//        binding.recyclerOrder.layoutManager = LinearLayoutManager(requireContext())
//        ordersAdapter.setItems(fakeOrders)
        }
    }
