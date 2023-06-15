package org.the_chance.honeymart.ui.feature

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentAuthBinding

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment: Int = R.layout.fragment_auth
    override val viewModel: ViewModel by viewModels()

}