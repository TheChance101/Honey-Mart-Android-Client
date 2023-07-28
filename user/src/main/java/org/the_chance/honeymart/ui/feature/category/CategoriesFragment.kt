package org.the_chance.honeymart.ui.feature.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentCategoriesBinding


@AndroidEntryPoint
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_categories
    override val viewModel: CategoryViewModel by viewModels()

    private val composeView: ComposeView by lazy {
        ComposeView(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return composeView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            HoneyMartTheme {
                CategoriesScreen()
            }
        }
    }

    override fun setup() {
//        initiateAdapter()
//        collectEffect()
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