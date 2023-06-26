package org.the_chance.honeymart.ui.feature.signup

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentSignupDetailsBinding

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupDetailsBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_signup_details
    override val viewModel: SignupViewModel by activityViewModels()
    private val args : SignupFragmentArgs by navArgs()
    override fun setup() {
        collectAction()
        viewModel.saveArgs(args)
    }

    override fun onResume() {
        super.onResume()
        setWindowVisibility(
            appBarVisibility = false,
            bottomNavVisibility = false,
            isTransparentStatusBar = false
        )
    }

    private fun collectAction() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { navigateToConfirmPassword() }
        }
    }

    private fun navigateToConfirmPassword() {
        val action = SignupFragmentDirections.actionSignupFragmentToSignupConfirmPasswordFragment()
        findNavController().navigate(action)
    }
}