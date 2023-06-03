package org.the_chance.ui.category

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.ui.BaseFragment
import org.the_chance.ui.R
import org.the_chance.ui.databinding.FragmentCategoriesBinding

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