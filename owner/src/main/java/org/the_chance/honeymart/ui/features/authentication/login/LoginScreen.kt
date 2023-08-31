package org.the_chance.honeymart.ui.features.authentication.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.HoneyAuthScaffold
import org.the_chance.honeymart.ui.components.NavigationHandler
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.navigateToMarketInfoScreen
import org.the_chance.honeymart.ui.features.authentication.signup.navigateToSignupScreen
import org.the_chance.honeymart.ui.features.authentication.waitingApprove.navigateToWaitingApproveScreen
import org.the_chance.honeymart.ui.features.category.navigateToCategoryScreen
import org.the_chance.honeymart.ui.navigation.LocalNavigationProvider
import org.the_chance.honymart.ui.composables.HoneyAuthFooter
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.HoneyTextFieldPassword
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                LoginUiEffect.ShowLoginErrorToastEffect -> {
                    Toast.makeText(
                        context,
                        state.validationToast.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                LoginUiEffect.NavigateToCategoriesEffect -> {
                    navController.navigateToCategoryScreen()
                }

                LoginUiEffect.ClickSignUpEffect -> {
                    navController.navigateToSignupScreen()
                }

                LoginUiEffect.NavigateToCreateMarketEffect -> {
                    navController.navigateToMarketInfoScreen()
                }

                LoginUiEffect.NavigateToWaitingApproveEffect -> {
                    navController.navigateToWaitingApproveScreen()
                }
            }
        })
    LoginContent(listener = viewModel, state = state)
}

@Composable
fun LoginContent(
    listener: LoginInteractionListener,
    state: LoginUiState,
) {
    Loading(state.authLoading)
    AnimatedVisibility(!state.authLoading) {
        HoneyAuthScaffold(
            modifier = Modifier.imePadding()
        ) {
            HoneyAuthHeader(
                titleColor = MaterialTheme.colorScheme.onSecondary,
                subTitleColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimens.space64),
                title = stringResource(R.string.welcome_back),
                subTitle = stringResource(R.string.login_to_discover_a_curated_selection_of_products_just_for_you),
            )
            Column {
                HoneyTextField(
                    text = state.emailState.value,
                    hint = stringResource(R.string.email),
                    iconPainter = painterResource(id = R.drawable.ic_email),
                    onValueChange = listener::onEmailInputChange,
                    errorMessage = state.emailState.errorState
                )

                HoneyTextFieldPassword(
                    text = state.passwordState.value,
                    hint = stringResource(R.string.password),
                    iconPainter = painterResource(id = R.drawable.ic_password),
                    onValueChange = listener::onPasswordInputChanged,
                    errorMessage = state.passwordState.errorState,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                )
            }
            HoneyFilledButton(
                label = stringResource(id = R.string.log_in),
                onClick = listener::onClickLogin,
                isButtonEnabled = state.isButtonEnabled,
                isLoading = state.isLoading,
            )
            HoneyAuthFooter(
                text = stringResource(R.string.don_t_have_an_account),
                textButtonText = stringResource(R.string.Sign_up),
                onTextButtonClicked = listener::onClickSignup,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}