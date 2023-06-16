package org.the_chance.honeymart.ui.feature.market

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentMarketsBinding

@AndroidEntryPoint
class MarketsFragment : BaseFragment<FragmentMarketsBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_markets
//    private lateinit var appBarLayout: AppBarLayout
//    private lateinit var imageLogoDefault: ShapeableImageView
//    private lateinit var imageLogoScrolled: ShapeableImageView

    private lateinit var appBarLayout: AppBarLayout
    private lateinit var logoImageView: ShapeableImageView
    override val viewModel: MarketViewModel by viewModels()
    override fun setup() {

//        appBarLayout = requireActivity().findViewById(R.id.appBarLayout)
//        imageLogoDefault = requireActivity().findViewById(R.id.image_logo)
//        imageLogoScrolled = requireActivity().findViewById(R.id.image_logo_scroll)
//
//        binding.recyclerMarkets
//            .addOnScrollListenerWithAppbarColor(requireContext(), this, appBarLayout)
//        binding.recyclerMarkets
//            .addOnScrollListenerWithImageVisibility(imageLogoDefault, imageLogoScrolled)





        handleOnBackPressed()
        initAdapter()
        collectAction()
    }

    private fun initAdapter() {
        val adapter = MarketAdapter(viewModel)
        binding.recyclerMarkets.adapter = adapter
    }

    private fun handleOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }
    }

    private fun collectAction() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { navigateToCategory(it) }
        }
    }

    private fun navigateToCategory(marketId: Long) {
        val action = MarketsFragmentDirections.actionMarketsFragmentToCategoriesFragment(marketId)
        findNavController().navigate(action)
    }
}