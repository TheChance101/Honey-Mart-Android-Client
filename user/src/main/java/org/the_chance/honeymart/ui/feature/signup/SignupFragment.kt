package org.the_chance.honeymart.ui.feature.signup

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentSignupBinding

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_signup
    override val viewModel: SignupViewModel by viewModels()

}