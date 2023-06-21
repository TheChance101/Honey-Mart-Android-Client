package org.the_chance.honeymart.ui.feature.product_details

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.honeymart.util.showSnackBar
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentProductDetailsBinding
import java.lang.Exception

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment: Int = R.layout.fragment_product_details
    override val viewModel: ProductDetailsViewModel by viewModels()

    override fun setup() {
        setupUserFlowWindowVisibility()
        initiateAdapter()
        navigateBack()
        collectEffect()
    }

    private fun initiateAdapter() {
        val adapter = ProductDetailsAdapter(viewModel)
        binding.recyclerProductImages.adapter = adapter
    }

    private fun navigateBack() {
        binding.iconBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { observeViewModelEvents(it) }
        }
    }

    private fun observeViewModelEvents(effect: ProductDetailsEvent) {
        when (effect) {
            is ProductDetailsEvent.AddToCartSuccess -> { showSnackBar(effect.message) }
            is ProductDetailsEvent.AddToCartError -> { showSnackBar(effect.error.toString()) }
        }
    }

}