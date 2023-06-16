package org.the_chance.honeymart.ui.feature.product

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentProductsBinding

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_products
    override val viewModel: ProductViewModel by viewModels()
    private val categoryAdapter:CategoryProductAdapter by lazy { CategoryProductAdapter(viewModel) }
    private val productAdapter:ProductAdapter by lazy { ProductAdapter(viewModel) }

    override fun setup() {
        initAdapters()
        collectEffect()
    }

    private fun initAdapters(){
        binding.recyclerCategory.adapter = categoryAdapter
        binding.recyclerProduct.adapter = productAdapter
    }
        private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { navigateToAuthenticate(it) }
        }
    }
       private fun navigateToAuthenticate(productId:Long) {
        val action = ProductsFragmentDirections
            .actionProductsFragmentToUserNavGraph()
        findNavController().navigate(action)
    }

}