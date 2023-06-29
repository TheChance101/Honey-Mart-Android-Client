package org.the_chance.honeymart.ui.feature.signup

import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.feature.ui_effect.AuthUiEffect
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.login.LoginDialog
import org.the_chance.honeymart.ui.feature.product.ProductsFragmentDirections
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsFragmentDirections
import org.the_chance.honeymart.util.AuthData
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

    private fun collectAction() {
        collect(viewModel.effect) { effect ->
            effect.getContentIfHandled()?.let { AuthUiEffect ->
                if (AuthUiEffect is AuthUiEffect.ClickSignUpEffect)
                    navigateToMainNav(AuthUiEffect.authData)
            }
        }
    }

    private fun navigateToMainNav(authData: AuthData) {
        val action = when (authData) {
            is AuthData.Products -> {
                ProductsFragmentDirections.actionGlobalProductsFragment(
                    authData.categoryId,
                    authData.marketId,
                    authData.position
                )
            }

            is AuthData.ProductDetails -> {
                ProductDetailsFragmentDirections.actionGlobalProductDetailsFragment(
                    authData.productId
                )
            }
        }
        changeNavGraphToMain().navigate(action)
        showDialog()
    }

    override fun showDialog() {
        dialog.show(parentFragmentManager, loginDialog)
    }

    private val loginDialog = "login_dialog"

    private fun changeNavGraphToMain(): NavController {
        val navController = findNavController()
        navController.setGraph(R.navigation.main_nav_graph)
        return navController
    }

}