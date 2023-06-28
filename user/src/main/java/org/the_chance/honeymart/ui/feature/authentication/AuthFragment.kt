package org.the_chance.honeymart.ui.feature.authentication

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.feature.ui_effect.AuthenticationUiEffect
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.util.AuthData
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentAuthBinding

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment: Int = R.layout.fragment_auth
    override val viewModel: AuthViewModel by viewModels()

    override fun setup() {
        addCallbacks()
    }

    override fun onResume() {
        super.onResume()
        setWindowVisibility(
            appBarVisibility = false,
            bottomNavVisibility = false,
            isTransparentStatusBar = false,
            isInAuthScreens = true
        )
    }
    private fun addCallbacks() {
        collectAction()
    }

    private fun collectAction() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { onEffect(it) }
        }
    }

    private fun onEffect(effect: AuthenticationUiEffect) {
        when (effect) {
            is AuthenticationUiEffect.ClickLoginEffect -> navigateToLogin(effect.authData)
            is AuthenticationUiEffect.ClickSignUpEffect -> navigateToSignUp(effect.authData)
        }
    }

    private fun navigateToLogin(authData: AuthData) {
        val action = AuthFragmentDirections.actionAuthFragmentToLoginFragment(authData)
        findNavController().navigate(action)
    }

    private fun navigateToSignUp(authData: AuthData) {
        val action = AuthFragmentDirections.actionAuthFragmentToSignupFragment(authData)
        findNavController().navigate(action)
    }

}