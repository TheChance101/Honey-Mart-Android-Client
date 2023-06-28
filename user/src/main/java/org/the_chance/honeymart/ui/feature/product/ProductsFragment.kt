package org.the_chance.honeymart.ui.feature.product

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.ui_effect.ProductUiEffect
import org.the_chance.honeymart.util.AuthData
import org.the_chance.honeymart.util.collect
import org.the_chance.honeymart.util.showSnackBar
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentProductsBinding

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_products
    override val viewModel: ProductViewModel by viewModels()
    private val categoryAdapter: CategoryProductAdapter by lazy { CategoryProductAdapter(viewModel) }
    private val productAdapter: ProductAdapter by lazy { ProductAdapter(viewModel) }

    override fun setup() {
        initAdapters()
        collectEffect()
    }

    private fun initAdapters() {
        binding.recyclerCategory.adapter = categoryAdapter
        binding.recyclerProduct.adapter = productAdapter
        setupScrollListenerForRecyclerView(binding.recyclerCategory)
        setupScrollListenerForRecyclerView(binding.recyclerProduct)

    }

    private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { onEffect(it) }
        }
    }

    private fun onEffect(effect: ProductUiEffect) {
        when (effect) {
            is ProductUiEffect.ClickProductEffect -> navigateToProductDetails(effect.productId)

            is ProductUiEffect.UnAuthorizedUserEffect -> navigateToAuthenticate(effect.authData)
            ProductUiEffect.AddedToWishListEffect -> {
                showSnackBar(getString(org.the_chance.design_system.R.string.addedToWishlistSuccessMessage))
            }

            ProductUiEffect.RemovedFromWishListEffect -> {
                showSnackBar(getString(org.the_chance.design_system.R.string.removedFromWishListSuccessMessage))

            }
        }
    }


    private fun navigateToAuthenticate(authData: AuthData.Products) {
        val action = ProductsFragmentDirections.actionProductsFragmentToUserNavGraph(
            authData
        )
        findNavController().navigate(action)

    }

    private fun navigateToProductDetails(productId: Long) {
        log(productId)
        val action =
            ProductsFragmentDirections.actionProductsFragmentToProductDetails(productId)
        findNavController().navigate(action)
    }
}