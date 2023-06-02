package org.the_chance.ui.market

import androidx.lifecycle.ViewModel
import org.the_chance.ui.BaseFragment
import org.the_chance.ui.R
import org.the_chance.ui.databinding.FragmentMarketsBinding

class MarketsFragment : BaseFragment<FragmentMarketsBinding>()  {

    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment: Int
        get() = R.layout.fragment_markets
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")

}