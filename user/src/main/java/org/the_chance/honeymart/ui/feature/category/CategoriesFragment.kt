package org.the_chance.honeymart.ui.feature.category

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentCategoriesBinding


@AndroidEntryPoint
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_categories
    override val viewModel: CategoryViewModel by viewModels()

    override fun setup() {
        initiateAdapter()
        collectEffect()
        setupMainFlowWindowVisibility()
    }

    private fun initiateAdapter() {
        val adapter = CategoryAdapter(viewModel)
        binding.recyclerCategories.adapter = adapter
        setupScrollListenerForRecyclerView(binding.recyclerCategories)
    }

    private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()
                ?.let { navigateToProduct(it.categoryId, it.marketId, it.position) }
        }
    }

    private fun navigateToProduct(categoryId: Long, marketId: Long, position: Int) {
        val action = CategoriesFragmentDirections
            .actionCategoriesFragmentToProductsFragment(categoryId, marketId, position)
        findNavController().navigate(action)
    }
}