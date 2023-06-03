package org.the_chance.honeymart.ui.feature.category

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.ui.category.CategoryViewModel
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentCategoriesBinding


@AndroidEntryPoint
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_categories
    override val viewModel: CategoryViewModel by viewModels()

    override fun setup() {
        initiateAdapter()
    }

    private fun initiateAdapter(){
        val adapter = CategoryAdapter(viewModel)
        binding.recyclerCategories.adapter = adapter
    }
}