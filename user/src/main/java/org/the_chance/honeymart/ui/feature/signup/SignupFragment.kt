package org.the_chance.honeymart.ui.feature.signup

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
    override fun setup() {
//        binding.buttonContinue.setOnClickListener {
//           findNavController().navigate(R.id.action_signupFragment_to_signupConfirmPasswordFragment)
//        }
        collectAction()
        makeStatusBarTransparent()

    }

    private fun collectAction() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { navigateToConfirmPassword() }
            Log.e("TAG", "collectAction: $effect")
        }
    }

    private fun navigateToConfirmPassword() {
        val action = SignupFragmentDirections.actionSignupFragmentToSignupConfirmPasswordFragment()
        findNavController().navigate(action)
    }
}