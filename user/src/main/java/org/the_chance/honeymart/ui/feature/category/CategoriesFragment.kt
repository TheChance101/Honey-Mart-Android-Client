package org.the_chance.honeymart.ui.feature.category

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.addOnScrollListenerWithAppbarColor
import org.the_chance.honeymart.util.addOnScrollListenerWithImageVisibility
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentCategoriesBinding


@AndroidEntryPoint
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_categories
    override val viewModel: CategoryViewModel by viewModels()
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var imageLogoDefault: ShapeableImageView
    private lateinit var imageLogoScrolled: ShapeableImageView
    override fun setup() {
        initiateAdapter()
        collectEffect()
    }
    private fun initiateAdapter() {
        val adapter = CategoryAdapter(viewModel)
        binding.recyclerCategories.adapter = adapter
        appBarLayout = requireActivity().findViewById(R.id.appBarLayout)
        imageLogoDefault = requireActivity().findViewById(R.id.image_logo)
        imageLogoScrolled = requireActivity().findViewById(R.id.image_logo_scroll)

        binding.recyclerCategories
            .addOnScrollListenerWithAppbarColor(requireContext(), this, appBarLayout)
        binding.recyclerCategories
            .addOnScrollListenerWithImageVisibility(imageLogoDefault, imageLogoScrolled)
    }

    private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { navigateToProduct(it.categoryId, it.marketId) }
        }
    }

    private fun navigateToProduct(categoryId: Long, marketId: Long) {
        val action = CategoriesFragmentDirections
            .actionCategoriesFragmentToProductsFragment(categoryId, marketId)
        findNavController().navigate(action)
    }
}