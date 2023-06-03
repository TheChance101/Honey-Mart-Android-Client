package org.the_chance.ui.product

import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.ui.BaseFragment
import org.the_chance.ui.R
import org.the_chance.ui.databinding.FragmentProductsBinding

@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_products
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")
}