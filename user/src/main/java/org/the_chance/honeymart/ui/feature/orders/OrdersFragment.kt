package org.the_chance.honeymart.ui.feature.orders

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
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
        ItemTouchHelper(swipe).attachToRecyclerView(binding.recyclerOrder)
    }

    private val swipe = object : SwipeToDeleteOrder() {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.absoluteAdapterPosition
            viewModel.updateOrders(position.toLong(),3)  // here you can update state
            showOrderDialog(position)
        }
    }

    private fun showOrderDialog(position: Int) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.layout_order_dialog)

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(org.the_chance.design_system.R.drawable.round_corner_dialog)

        val btnCancel = dialog.findViewById<Button>(R.id.button_cancel)
        val btnSure = dialog.findViewById<Button>(R.id.button_sure)

        btnSure.setOnClickListener {
            ordersAdapter.removeItem(position)
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.show()
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
}