package org.the_chance.honeymart.ui.feature.orderdetails

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentOrderDetailsBinding

@AndroidEntryPoint
class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {

    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_order_details
    override val viewModel: OrderDetailsViewModel by viewModels()
    private val orderDetailsAdapter: OrderDetailsAdapter by lazy { OrderDetailsAdapter(viewModel) }


    override fun setup() {
        initAdapters()
    }


    private fun initAdapters() {
        binding.recyclerOrderDetails.adapter = orderDetailsAdapter
    }

}