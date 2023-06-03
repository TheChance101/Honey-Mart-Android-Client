package org.the_chance.ui.category

import androidx.fragment.app.viewModels
import org.the_chance.ui.BaseFragment
import org.the_chance.ui.R
import org.the_chance.ui.databinding.FragmentCategoriesBinding

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>()  {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_categories
    override val viewModel: CategoryViewModel by viewModels()
}