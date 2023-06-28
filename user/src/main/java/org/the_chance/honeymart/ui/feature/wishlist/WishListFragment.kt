package org.the_chance.honeymart.ui.feature.wishlist

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.feature.ui_effect.WishListUiEffect
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.honeymart.util.showSnackBar
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
        collectEffect()
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.markets_graph)
        }
    }

    private fun initAdapters() {
        binding.recyclerWishList.adapter = wishListAdapter
        setupScrollListenerForRecyclerView(binding.recyclerWishList)
    }

    private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let {
                onEffect(it)
            }
        }
    }

    private fun onEffect(effect: WishListUiEffect) {
        when (effect) {
            is WishListUiEffect.ClickProductEffect -> navigateToProductDetails(effect.productId)
            is WishListUiEffect.UnAuthorizedUserEffect -> TODO()
            is WishListUiEffect.ClickDiscoverEffect -> navigateToMarkets()
            WishListUiEffect.DeleteProductFromWishListEffect -> {
                showSnackBar(getString(org.the_chance.design_system.R.string.removedFromWishListSuccessMessage))
            }
        }
    }

    private fun navigateToProductDetails(productId: Long) {
        val action = WishListFragmentDirections.actionGlobalProductDetailsFragment(productId)
        findNavController().navigate(action)
    }

    private fun navigateToMarkets() {
        val action = WishListFragmentDirections.actionWishListFragmentToMarketsFragment()
        findNavController().navigate(action)

    }

    override fun onResume() {
        super.onResume()
        viewModel.getWishListProducts()
    }
}