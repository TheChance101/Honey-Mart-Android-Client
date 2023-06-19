package org.the_chance.honeymart.ui.feature.orders

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentOrdersBinding
@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_products
    override val viewModel: OrderViewModel by viewModels()
    private val ordersAdapter: OrdersAdapter by lazy { OrdersAdapter(viewModel) }

    override fun setup() {

    }

}