package org.the_chance.honeymart.ui.feature.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.AuthData
import org.the_chance.honeymart.util.collect
import org.the_chance.honeymart.util.showSnackBar
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentProductsBinding

@AndroidEntryPoint
class ProductsFragment : Fragment(){
//    override val TAG: String = this::class.simpleName.toString()
//    override val layoutIdFragment = R.layout.fragment_products
     val viewModel: ProductViewModel by viewModels()
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireActivity()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            // You're in Compose world!
            HoneyMartTheme {
                ProductsScreen()
            }
        }
    }
//    private val categoryAdapter: CategoryProductAdapter by lazy { CategoryProductAdapter(viewModel) }
//    private val productAdapter: ProductAdapter by lazy { ProductAdapter(viewModel) }

//    override fun setup() {
//        initAdapters()
//        collectEffect()
//    }
//
//    private fun initAdapters() {
//        binding.recyclerCategory.adapter = categoryAdapter
//        binding.recyclerProduct.adapter = productAdapter
//        setupScrollListenerForRecyclerView(binding.recyclerCategory)
//        setupScrollListenerForRecyclerView(binding.recyclerProduct)
//
//    }
//
//    private fun collectEffect() {
//        collect(viewModel.effect) { effect ->
//            effect.getContentIfHandled()?.let { onEffect(it) }
//        }
//    }
//
//    private fun onEffect(effect: ProductUiEffect) {
//        when (effect) {
//            is ProductUiEffect.ClickProductEffect -> navigateToProductDetails(effect.productId)
//
//            is ProductUiEffect.UnAuthorizedUserEffect -> navigateToAuthenticate(effect.authData)
//            ProductUiEffect.AddedToWishListEffect -> {
//                showSnackBar(getString(org.the_chance.design_system.R.string.addedToWishlistSuccessMessage))
//            }
//
//            ProductUiEffect.RemovedFromWishListEffect -> {
//                showSnackBar(getString(org.the_chance.design_system.R.string.removedFromWishListSuccessMessage))
//
//            }
//        }
//    }
//
//
//    private fun navigateToAuthenticate(authData: AuthData.Products) {
//        val action = ProductsFragmentDirections.actionProductsFragmentToUserNavGraph(
//            authData
//        )
//        findNavController().navigate(action)
//
//    }
//
//    private fun navigateToProductDetails(productId: Long) {
//        log(productId)
//        val action =
//            ProductsFragmentDirections.actionProductsFragmentToProductDetails(productId)
//        findNavController().navigate(action)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        viewModel.getData()
//    }
}