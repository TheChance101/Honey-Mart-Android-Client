package org.the_chance.honeymart.ui.feature.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DismissDirection.EndToStart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.cart.Composeables.CartCardView
import org.the_chance.honeymart.ui.feature.cart.Composeables.CartItem
import org.the_chance.honeymart.ui.feature.cart.Composeables.SwipeDelete
import org.the_chance.honeymart.util.collect
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentCartBinding

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment: Int = R.layout.fragment_cart
    override val viewModel: CartViewModel by viewModels()
    private val cartAdapter: CartAdapter by lazy { CartAdapter(viewModel) }
    private lateinit var composeView: ComposeView
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireActivity()).also {
            composeView = it
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            HoneyMartTheme {
                viewModel.getChosenCartProducts()
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {

                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(cartViewModel.state.value.products.size)
                            {
                                val dismissState = rememberDismissState()
                                SwipeToDismiss(
                                    state = dismissState,
                                    background = { SwipeDelete(state = dismissState) },
                                    directions = setOf(EndToStart),
                                    dismissContent = {
                                        CartItem()
                                    })
                            }
                        }
                        CartCardView(
                            totalPrice = cartViewModel.state.value.total.toString(),
                        )

                    }
                    //CartPlaceholder()
                    //NoConnectionLayout()
                }
            }
        }
    }


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
//        binding.recyclerCartList.adapter = cartAdapter
//        binding.recyclerCartList.layoutManager = LinearLayoutManager(requireContext())
//        ItemTouchHelper(swipe).attachToRecyclerView(binding.recyclerCartList)
//        setupScrollListenerForRecyclerView(binding.recyclerCartList)
//        collectEffect()
    }

//    private val swipe = object : SwipeHelper() {
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder,
//        ) = true
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            val position = binding.recyclerCartList.getChildAdapterPosition(viewHolder.itemView)
//            showOrderDialog(position, viewHolder)
//        }
//    }

    private fun showOrderDialog(position: Int, viewHolder: RecyclerView.ViewHolder) {
//        val dialog = Dialog(requireContext())
//        dialog.setContentView(R.layout.layout_order_dialog)
//
//        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
//        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//        dialog.window?.setBackgroundDrawableResource(org.the_chance.design_system.R.drawable.round_corner_dialog)
//
//        val btnCancel = dialog.findViewById<Button>(R.id.button_cancel)
//        val btnSure = dialog.findViewById<Button>(R.id.button_sure)
//        btnSure.setOnClickListener {
//            cartAdapter.removeItem(position)
//            viewModel.deleteCart(position.toLong())
//            dialog.dismiss()
//        }
//        btnCancel.setOnClickListener {
//            dialog.dismiss()
//            cartAdapter.notifyItemChanged(position)
//        }
//        dialog.setCancelable(false)
//        dialog.show()
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

