package org.the_chance.ui.category

import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.ui.BaseFragment
import org.the_chance.ui.R
import org.the_chance.ui.databinding.FragmentCategoriesBinding

@AndroidEntryPoint
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_categories
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")

}