package org.the_chance.honeymart.ui.feature.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseDialog
import org.the_chance.honeymart.ui.feature.signup.SignupViewModel
import org.the_chance.user.R
import org.the_chance.user.databinding.LayoutLoginDialogBinding

@AndroidEntryPoint
class LoginDialogFragment : BaseDialog<LayoutLoginDialogBinding>(90) {
    override val layoutIdDialog: Int = R.layout.layout_login_dialog
    override val viewModel: SignupViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(true)

    }
}