package org.the_chance.honeymart.ui.feature.order_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.login.LoginViewModel
import org.the_chance.honeymart.util.collect
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentOrderDetailsBinding

@AndroidEntryPoint
class OrderDetailsFragment : Fragment() {

    val viewModel: OrderDetailsViewModel by viewModels()
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireActivity()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            // You're in Compose world!
            HoneyMartTheme {
                OrderDetailsScreen(
                    onClickItemOrderDetails = {navigateToProductsDetails(0)}
                )
            }
        }
    }


//    override val TAG: String = this::class.java.simpleName
//    override val layoutIdFragment = R.layout.fragment_order_details
//    override val viewModel: OrderDetailsViewModel by viewModels()
//    private val orderDetailsAdapter: OrderDetailsAdapter by lazy { OrderDetailsAdapter(viewModel) }
//
//
//
//    override fun setup() {
//        initAdapters()
//        collectEffect()
//    }
//
//    private fun initAdapters() {
//        binding.recyclerOrderDetails.adapter = orderDetailsAdapter
//    }
//
    private fun collectEffect() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let {
                onEffect(it)
            }
        }
    }
//
    private fun onEffect(effect: OrderDetailsUiEffect) {
        when (effect) {
            is OrderDetailsUiEffect.ClickProductEffect ->navigateToProductsDetails(effect.orderId)
        }
    }
//
    private fun navigateToProductsDetails(orderId: Long) {
        val action = OrderDetailsFragmentDirections.actionGlobalProductDetailsFragment(orderId)
        findNavController().navigate(action)
    }
}