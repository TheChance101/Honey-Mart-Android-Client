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

    private fun initAdapters() {
        binding.recyclerOrderDetails.adapter = orderDetailsAdapter
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_order_details, container, false)
//    }

}