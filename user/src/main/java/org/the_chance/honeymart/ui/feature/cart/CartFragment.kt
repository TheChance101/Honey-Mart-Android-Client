package org.the_chance.honeymart.ui.feature.cart

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.uistate.CartListProductUiState
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentCartBinding

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment: Int = R.layout.fragment_cart
    override val viewModel: CartViewModel by viewModels()
    private val cartAdapter: CartAdapter by lazy { CartAdapter(viewModel) }

    override fun setup() {
        initAdapters()
        handleOnBackPressed()
    }


    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.marketsFragment)
        }
    }

    private fun initAdapters() {
        val fakeCarts = listOf(
            CartListProductUiState(1, "Product 1", 100.0, 10),
            CartListProductUiState(2, "Product 2", 200.5, 3),
            CartListProductUiState(3, "Product 3", 300.9, 2),
            CartListProductUiState(4, "Product 4", 400.9, 5),

            )
        binding.recyclerCartList.adapter = cartAdapter
        binding.recyclerCartList.layoutManager = LinearLayoutManager(requireContext())
        cartAdapter.setItems(fakeCarts)
        ItemTouchHelper(swipe).attachToRecyclerView(binding.recyclerCartList)

    }

    private val swipe = object : SwipeHelper() {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val item = cartAdapter.getItemId(position)
            viewModel.onClickDeleteCart(item)
        }
    }
}
