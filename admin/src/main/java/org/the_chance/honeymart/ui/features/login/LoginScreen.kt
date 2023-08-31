package org.the_chance.honeymart.ui.features.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.HoneyAuthScaffold
import org.the_chance.honeymart.ui.composables.NavigationHandler
import org.the_chance.honeymart.ui.features.markets.navigateToMarketsScreen
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.HoneyTextFieldPassword
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                LoginUiEffect.ShowEmptyFieldsToastEffect -> {
                    Toast.makeText(
                        context,
                        state.validationToast.messageEmptyFields,
                        Toast.LENGTH_LONG
                    ).show()
                }

                LoginUiEffect.ShowInvalidDetailsToastEffect -> {
                    Toast.makeText(
                        context,
                        state.validationToast.messageInvalidDetails,
                        Toast.LENGTH_LONG
                    ).show()
                }

                LoginUiEffect.ClickLoginEffect -> {
                    navController.navigateToMarketsScreen()
                }
            }
        })
    LoginContent(viewModel, state)
}

@Composable
fun LoginContent(
    listener: LoginInteractionListener,
    state: LoginUiState,
) {
    Loading(state.isLoading)
    AnimatedVisibility(!state.isLoading) {
        HoneyAuthScaffold(
            modifier = Modifier.imePadding()
        ) {
            HoneyAuthHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimens.space64),
                title = stringResource(R.string.welcome_back),
                subTitle = stringResource(R.string.login_to_discover_a_curated_selection_of_products_just_for_you),
            )
            Column {
                HoneyTextField(
                    text = state.email.value,
                    hint = stringResource(R.string.email),
                    iconPainter = painterResource(id = R.drawable.ic_email),
                    onValueChange = listener::onEmailInputChange,
                    errorMessage = state.email.error
                )

                HoneyTextFieldPassword(
                    text = state.password.value,
                    hint = stringResource(R.string.password),
                    iconPainter = painterResource(id = R.drawable.ic_password),
                    onValueChange = listener::onPasswordInputChanged,
                    errorMessage = state.password.error,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                )
            }
            HoneyFilledButton(
                label = stringResource(id = R.string.log_in),
                onClick = listener::onClickLogin,
                isLoading = state.isAuthenticating,
            )
        }
    }
}