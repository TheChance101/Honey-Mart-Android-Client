package org.the_chance.honeymart.ui.feature.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.feature.login.LoginViewModel
import org.the_chance.honeymart.util.AuthData
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@AndroidEntryPoint
class AuthFragment : Fragment()  {

    val viewModel: AuthViewModel by viewModels()
    private lateinit var composeView: ComposeView

    val authData = AuthFragmentArgs.fromSavedStateHandle(SavedStateHandle()).AuthData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireActivity()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            HoneyMartTheme {
                AuthScreen(this,viewModel, authData)
            }
        }
    }

    private fun navigateToSignUp(authData: AuthData) {
        val action = AuthFragmentDirections.actionAuthFragmentToSignupFragment(authData)
        findNavController().navigate(action)
    }
}