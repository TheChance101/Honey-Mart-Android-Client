package org.the_chance.honeymart.ui.feature.orders

import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.product.ProductsFragmentDirections
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
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteOrder(requireContext()) { position ->
            (binding.recyclerOrder.adapter as OrdersAdapter).removeItem(position)
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerOrder)
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
            effect.getContentIfHandled()?.let { onEffect(it) }
        }
    }

    private fun onEffect(effect: OrderUiEffect) {
        when (effect) {
            is OrderUiEffect.UnAuthorizedUserEffect -> navigateToAuthenticate()
            is OrderUiEffect.ClickDiscoverMarketsEffect -> navigateToMarkets()
            is OrderUiEffect.ClickOrderEffect -> TODO()
        }
    }

    private fun navigateToAuthenticate() {
        val action = OrdersFragmentDirections.actionOrdersFragmentToUserNavGraph()
        findNavController().navigate(action)
    }

    private fun navigateToMarkets() {
        val action = OrdersFragmentDirections.actionOrdersFragmentToMarketsFragment()
        findNavController().navigate(action)
    }

    private fun showAlertOrderDialog(execute: () -> Unit) {
        val customView =
            LayoutInflater.from(requireContext()).inflate(R.layout.layout_order_dialog, null)

        val buttonSure = customView.findViewById<Button>(R.id.button_sure)
        val buttonCancel = customView.findViewById<Button>(R.id.button_cancel)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(customView)
            .setCancelable(false)
            .show()

        buttonSure.setOnClickListener {
            execute()
            dialog.dismiss()
        }

        buttonCancel.setOnClickListener {
            Snackbar.make(
                requireView(),
                getString(org.the_chance.design_system.R.string.cancel),
                Snackbar.LENGTH_SHORT
            )
                .show()
            dialog.dismiss()
        }
    }
}
