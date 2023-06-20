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

        binding.recyclerCartList.adapter = cartAdapter
        binding.recyclerCartList.layoutManager = LinearLayoutManager(requireContext())
        cartAdapter.setItems(viewModel.fakeCarts)
        ItemTouchHelper(swipe).attachToRecyclerView(binding.recyclerCartList)

    }

    private val swipe = object : SwipeHelper() {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val item = cartAdapter.getItemId(position)
            viewModel.onClickDeleteCart(item)
            cartAdapter.notifyItemRemoved(position)


        }
    }
}
