package org.the_chance.honeymart.ui.feature.signup

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.login.LoginDialog
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentSignupConformPasswordBinding

@AndroidEntryPoint
class SignupConfirmPasswordFragment : BaseFragment<FragmentSignupConformPasswordBinding>(),
    LoginDialog {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_signup_conform_password
    override val viewModel: SignupViewModel by activityViewModels()
    override fun setup() {
        collectAction()
        setupUserFlowWindowVisibility()
    }

    private fun collectAction() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { navigateToMainNav() }
        }
    }

    private fun navigateToMainNav() {
        val action =
            SignupConfirmPasswordFragmentDirections.actionSignupConfirmPasswordFragmentPop()
        findNavController().navigate(action)
        showDialog()
    }

    override fun showDialog() {
        dialog.show(parentFragmentManager, loginDialog)
    }

    private val loginDialog = "login_dialog"

}