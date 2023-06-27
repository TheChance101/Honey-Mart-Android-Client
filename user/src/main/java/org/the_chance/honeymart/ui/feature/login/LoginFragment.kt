package org.the_chance.honeymart.ui.feature.login

import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.feature.authentication.AuthenticationUiEffect
import org.the_chance.honeymart.ui.feature.product.ProductsFragmentDirections
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsFragmentDirections
import org.the_chance.honeymart.util.AuthData
import org.the_chance.honeymart.util.collect
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val TAG: String = this::class.simpleName.toString()
    override val layoutIdFragment = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()
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
    }

    private fun navigateToSignUp(authData: AuthData) {
        val action = LoginFragmentDirections.actionLoginFragmentToSignupFragment(authData)
        findNavController().navigate(action)
    }

    private fun changeNavGraphToMain(): NavController {
        val navController = findNavController()
        navController.setGraph(R.navigation.main_nav_graph)
        return navController
    }


}