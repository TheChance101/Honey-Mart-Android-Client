package org.the_chance.honeymart.ui.feature.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginDialog {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()
    override fun setup() {
        collectAction()
        setupUserFlowWindowVisibility()
        binding.textSignup.setOnClickListener {
            navigateToSignup()
        }
    }


    private fun collectAction() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { navigateToMainNav() }
        }
    }

    private fun navigateToSignup() {
        val action =
            LoginFragmentDirections.actionLoginFragmentToSignupFragment()
        findNavController().navigate(action)
    }

    private fun navigateToMainNav() {
        val action = LoginFragmentDirections.actionLoginFragmentPop()
        findNavController().navigate(action)
        showDialog()
    }

    override fun showDialog() {
        dialog.show(parentFragmentManager, loginDialog)
    }

    private val loginDialog = "login_dialog"


}