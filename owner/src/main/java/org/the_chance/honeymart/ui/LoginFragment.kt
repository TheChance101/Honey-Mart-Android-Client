package org.the_chance.honeymart.ui

import androidx.fragment.app.viewModels
import org.the_chance.honeymart.base.BaseFragment
import org.the_chance.owner.R
import org.the_chance.owner.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()
    override fun setup() {}

}