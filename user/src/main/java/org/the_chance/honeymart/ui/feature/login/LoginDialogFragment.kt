package org.the_chance.honeymart.ui.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@AndroidEntryPoint
class LoginDialogFragment : DialogFragment() {
    private lateinit var composeView: ComposeView
//    override val layoutIdDialog: Int = R.layout.layout_login_dialog
//    override val viewModel: SignupViewModel by activityViewModels()

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
                CustomDialog(openDialogCustom = remember { mutableStateOf(false) })
            }
        }
    }
}