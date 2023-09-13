package org.the_chance.honeymart.ui.features.authentication.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.components.NavigationHandler
import org.the_chance.honeymart.ui.features.authentication.login.navigateToLogin
import org.the_chance.honeymart.ui.features.authentication.signup.composables.OwnerFieldsScaffold
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.MarketInfoScreen
import org.the_chance.honeymart.ui.features.authentication.waitingApprove.navigateToWaitingApproveScreen
import org.the_chance.honeymart.ui.features.category.navigateToCategoryScreen
import org.the_chance.honeymart.ui.navigation.LocalNavigationProvider

@Composable
fun SignupScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                SignupUiEffect.ShowValidationToast -> {
                    Toast.makeText(
                        context,
                        state.validationToast.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                SignupUiEffect.ClickLoginEffect -> {
                    navController.navigateToLogin()
                }
                SignupUiEffect.NavigateToCategoriesEffect -> {
                    navController.navigateToCategoryScreen()
                }

                SignupUiEffect.NavigateToWaitingApproveEffect -> {
                    navController.navigateToWaitingApproveScreen()
                }

                SignupUiEffect.ClickLogoutEffect -> {
                    navController.navigateToLogin()
                }
            }
        })
    SignupContent(listener = viewModel, state = state)
}

@Composable
fun SignupContent(
    state: SignupUiState,
    listener: SignupInteractionListener,
) {
    ContentVisibility(state = !state.isOwnerAccountCreated) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            OwnerFieldsScaffold(state = state, listener = listener)
        }
    }
    ContentVisibility(state = state.isOwnerAccountCreated) {
        MarketInfoScreen()
    }
}


@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun PreviewSignupScreen() {
    SignupScreen()
}