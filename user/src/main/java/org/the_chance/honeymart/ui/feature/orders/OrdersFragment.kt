package org.the_chance.honeymart.ui.feature.orders

import android.app.Dialog
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.uistate.OrderStates
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentOrdersBinding

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_orders
    override val viewModel: OrderViewModel by viewModels()
    private val ordersAdapter: OrdersAdapter by lazy { OrdersAdapter(viewModel) }
    private lateinit var swipe: SwipeToDeleteOrder
    private lateinit var touchHelper: ItemTouchHelper

    override fun setup() {
        initAdapter()
        handleOnBackPressed()
        collectEffect()
        attachSwipe(OrderStates.PROCESSING)
    }

    private fun initAdapter() {
        binding.recyclerOrder.adapter = ordersAdapter
        setupScrollListenerForRecyclerView(binding.recyclerOrder)
    }

    private fun attachSwipe(orderState: OrderStates) {
        swipe = object : SwipeToDeleteOrder(orderState) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                when (orderState) {
                    OrderStates.PROCESSING -> {
                        showAlertOrderDialog() {
                            viewModel.updateOrders(
                                position.toLong(),
                                3
                            )
                        }
                    }

                    OrderStates.DONE -> {
                        showAlertOrderDialog() {
                            viewModel.updateOrders(
                                position.toLong(),
                                4
                            )
                        }
                    }

                    OrderStates.CANCELED -> {
                        showAlertOrderDialog() {
                            viewModel.updateOrders(
                                position.toLong(),
                                4
                            )
                        }
                    }
                }
            }
        }
        if (::touchHelper.isInitialized) {
            touchHelper.attachToRecyclerView(null)
            touchHelper = ItemTouchHelper(swipe)
            touchHelper.attachToRecyclerView(binding.recyclerOrder)
        } else {
            touchHelper = ItemTouchHelper(swipe)
            touchHelper.attachToRecyclerView(binding.recyclerOrder)
        }
    }

    private fun showAlertOrderDialog(execute: () -> Unit) {
        val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.layout_order_dialog)
            dialog.setCancelable(false)
            dialog.show()
        val buttonSure = dialog.findViewById<Button>(R.id.button_sure)
        val buttonCancel = dialog.findViewById<Button>(R.id.button_cancel)

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(org.the_chance.design_system.R.drawable.round_corner_dialog)

        buttonSure.setOnClickListener {
            execute()
            dialog.dismiss()
        }

        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
    }



    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.marketsFragment)
        }
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
            OrderUiEffect.ClickCanceled -> attachSwipe(OrderStates.CANCELED)
            OrderUiEffect.ClickDone -> attachSwipe(OrderStates.DONE)
            OrderUiEffect.ClickProcessing -> attachSwipe(OrderStates.PROCESSING)
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

    private fun navigateToOrdersDetails(orderId: Long) {
        val action = OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(orderId)
        findNavController().navigate(action)
    }
}