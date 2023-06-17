package org.the_chance.honeymart.ui.feature.login

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.signup.SignupConfirmPasswordFragmentDirections
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()
    override fun setup() {
        collectAction()
    }


    private fun collectAction() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { navigateToMainNav() }
            Log.e("TAG", "collectAction: $effect")
        }
    }

    private fun navigateToMainNav() {
        val action =
            LoginFragmentDirections.actionLoginFragmentPop()
        findNavController().navigate(action)
    }
}