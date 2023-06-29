package org.the_chance.honeymart.ui.feature.cart

import android.app.Dialog
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.feature.ui_effect.CartUiEffect
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentCartBinding

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment: Int = R.layout.fragment_cart
    override val viewModel: CartViewModel by viewModels()
    private val cartAdapter: CartAdapter by lazy { CartAdapter(viewModel) }


    override fun setup() {
        viewModel.getChosenCartProducts()
        initAdapters()
        handleOnBackPressed()
    }


    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.markets_graph)
        }
    }

    private fun initAdapters() {
        binding.recyclerCartList.adapter = cartAdapter
        binding.recyclerCartList.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipe).attachToRecyclerView(binding.recyclerCartList)
        setupScrollListenerForRecyclerView(binding.recyclerCartList)
        collectEffect()
    }

    private val swipe = object : SwipeHelper() {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = binding.recyclerCartList.getChildAdapterPosition(viewHolder.itemView)
            showOrderDialog(position, viewHolder)
        }
    }

    private fun showOrderDialog(position: Int, viewHolder: RecyclerView.ViewHolder) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.layout_order_dialog)

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawableResource(org.the_chance.design_system.R.drawable.round_corner_dialog)

        val btnCancel = dialog.findViewById<Button>(R.id.button_cancel)
        val btnSure = dialog.findViewById<Button>(R.id.button_sure)
        btnSure.setOnClickListener {
            cartAdapter.removeItem(position)
            viewModel.deleteCart(position.toLong())
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
            cartAdapter.notifyItemChanged(position)
        }
        dialog.setCancelable(false)
        dialog.show()
    }


    private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let {
                onEffect(it)
            }
        }
    }

    private fun onEffect(effect: CartUiEffect) {
        when (effect) {
            is CartUiEffect.ClickDiscoverEffect -> navigateToMarkets()
            is CartUiEffect.ClickOrderEffect -> navigateToOrders()
        }
    }

    private fun navigateToOrders() {
        val action = CartFragmentDirections.actionCartFragmentToCartBottomFragment()
        findNavController().navigate(action)
    }

    private fun navigateToMarkets() {
        val action = CartFragmentDirections.actionCartFragmentToMarketsFragment()
        findNavController().navigate(action)
    }
}

