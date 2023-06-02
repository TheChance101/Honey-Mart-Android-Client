package org.the_chance.ui.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import org.the_chance.ui.BaseFragment
import org.the_chance.ui.R

class MarketsFragment : BaseFragment<FragmentMarketsBinding>()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override val TAG: String = this::class.simpleName.toString()

    override val layoutIdFragment: Int
        get() = R.layout.fragment_markets
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")

}