package org.the_chance.honeymart.ui.feature.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@AndroidEntryPoint
class WishListFragment : Fragment() {
    val viewModel: WishListViewModel by viewModels()
    private lateinit var composeView: ComposeView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireActivity()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            HoneyMartTheme {
                WishListScreen()
            }
        }
    }


//    override val TAG: String = this::class.java.simpleName
//    override val layoutIdFragment: Int = R.layout.fragment_wish_list
//    override val viewModel: WishListViewModel by viewModels()
//    private val wishListAdapter: WishListAdapter by lazy { WishListAdapter(viewModel) }

//    override fun setup() {
//        handleOnBackPressed()
//        initAdapters()
//        collectEffect()
//    }

//    private fun handleOnBackPressed() {
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            findNavController().navigate(R.id.markets_graph)
//        }
//    }
//
//    private fun initAdapters() {
//        binding.recyclerWishList.adapter = wishListAdapter
//        setupScrollListenerForRecyclerView(binding.recyclerWishList)
//    }
//
//    private fun collectEffect() {
//        collect(viewModel.effect) { effect ->
//            effect.getContentIfHandled()?.let {
//                onEffect(it)
//            }
//        }


//    private fun onEffect(effect: WishListUiEffect) {
//        when (effect) {
//            is WishListUiEffect.ClickProductEffect -> navigateToProductDetails(effect.productId)
//            is WishListUiEffect.UnAuthorizedUserEffect -> TODO()
//            is WishListUiEffect.ClickDiscoverEffect -> navigateToMarkets()
//            WishListUiEffect.DeleteProductFromWishListEffect -> {
//                showSnackBar(getString(org.the_chance.design_system.R.string.removedFromWishListSuccessMessage))
//            }
//        }
//    }

//    private fun navigateToProductDetails(productId: Long) {
//        val action = WishListFragmentDirections.actionGlobalProductDetailsFragment(productId)
//        findNavController().navigate(action)
//    }

//    private fun navigateToMarkets() {
//        val action = WishListFragmentDirections.actionWishListFragmentToMarketsFragment()
//        findNavController().navigate(action)
//
//    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.getWishListProducts()
//    }


}