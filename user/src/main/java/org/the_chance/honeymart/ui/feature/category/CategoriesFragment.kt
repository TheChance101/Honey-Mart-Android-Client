package org.the_chance.honeymart.ui.feature.category

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.util.EventObserve
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentCategoriesBinding


@AndroidEntryPoint
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_categories
    override val viewModel: CategoryViewModel by viewModels()
    private val args: CategoriesFragmentArgs by navArgs()
    override fun setup() {
        initiateAdapter()
        viewModel.getAllCategory(args.marketId)

        observeOnCategory()
    }

    private fun initiateAdapter() {
        val adapter = CategoryAdapter(viewModel)
        binding.recyclerCategories.adapter = adapter
    }

    private fun observeOnCategory() {
        viewModel.uiCategoryState.observe(this, EventObserve { categoryId ->
            navigateToProduct(categoryId, args.marketId)
        })
    }

    private fun navigateToProduct(categoryId: Long, productMarketId: Long) {
        val action = CategoriesFragmentDirections
            .actionCategoriesFragmentToProductsFragment(categoryId, productMarketId)
        findNavController().navigate(action)
    }
}