package org.the_chance.honeymart.ui.feature.wishlist

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.product.ProductAdapter
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentWishListBinding

@AndroidEntryPoint
class WishListFragment : BaseFragment<FragmentWishListBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment: Int = R.layout.fragment_wish_list
    override val viewModel: WishListViewModel by viewModels()
    private val wishListAdapter: WishListAdapter by lazy { WishListAdapter(viewModel) }

    override fun setup() {
        handleOnBackPressed()
        initAdapters()
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.marketsFragment)
        }
    }

    private fun initAdapters() {
        binding.recyclerWishList.adapter = wishListAdapter
    }
}