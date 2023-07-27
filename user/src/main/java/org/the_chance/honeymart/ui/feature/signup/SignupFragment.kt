package org.the_chance.honeymart.ui.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@AndroidEntryPoint
class SignupFragment : Fragment() {
    private lateinit var composeView: ComposeView
    private val args : SignupFragmentArgs by navArgs()

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
                SignupScreen(this,args.AuthData, args)
            }
        }
    }
}