package org.the_chance.honeymart.ui.feature.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.feature.signup.navigateToSignupScreen
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white
import org.the_chance.honymart.ui.theme.white200

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val navController = LocalNavigationProvider.current
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                LoginUiEffect.ClickLoginEffect -> navController.popBackStack(
                    Screen.AuthenticationScreen.route,
                    true
                )

                LoginUiEffect.ClickSignUpEffect -> navController.navigateToSignupScreen()
                LoginUiEffect.ShowToastEffect -> Toast.makeText(
                    context,
                    "Email or password not valid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LoginContent(listener = viewModel, state = state)
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    listener: LoginInteractionListener,
    state: LoginUiState,
) {
    Loading(state.isLoading)

    ContentVisibility(state = !state.isLoading) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(R.drawable.background_frame),
                    contentDescription = null,
                    modifier = modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space24),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16)
                ) {
                    Text(
                        text = stringResource(R.string.welcome_back),
                        style = Typography.headlineMedium.copy(color = white),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(
                            R.string.reconnect_with_your_favorite_brands_and_saved_items_log_in_today
                        ),
                        style = Typography.bodyMedium.copy(color = white),
                        textAlign = TextAlign.Center
                    )
                }
            }
            HoneyTextField(
                modifier = Modifier.padding(end = MaterialTheme.dimens.space16),
                oneLineOnly = true,
                text = state.email,
                hint = stringResource(R.string.email),
                iconPainter = painterResource(id = R.drawable.ic_email),
                onValueChange = listener::onEmailInputChange,
                errorMessage = when (state.emailState) {
                    ValidationState.BLANK_EMAIL -> "email cannot be blank"
                    ValidationState.INVALID_EMAIL -> "Invalid email"
                    else -> ""
                },
                color = white200
            )
            HoneyTextField(
                isPassword = PasswordVisualTransformation(),
                modifier = Modifier.padding(end = MaterialTheme.dimens.space16),
                oneLineOnly = true,
                text = state.password,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                hint = stringResource(R.string.password),
                iconPainter = painterResource(id = R.drawable.ic_password),
                onValueChange = listener::onPasswordInputChanged,
                errorMessage = when (state.passwordState) {
                    ValidationState.BLANK_PASSWORD -> "Password cannot be blank"
                    ValidationState.INVALID_PASSWORD -> "Invalid password"
                    ValidationState.INVALID_PASSWORD_LENGTH -> "Password must be at least 8 characters"
                    else -> ""
                },
                color = white200
            )
            HoneyFilledButton(
                label = stringResource(id = R.string.log_in),
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space40
                ),
                onClick = listener::onClickLogin,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = MaterialTheme.dimens.space32)
            ) {
                Text(
                    text = stringResource(R.string.don_t_have_an_account),
                    style = Typography.displaySmall.copy(color = black37),
                    textAlign = TextAlign.Center
                )
                TextButton(
                    onClick = listener::onClickSignup,
                    colors = ButtonDefaults.textButtonColors(Color.Transparent)
                ) {
                    Text(
                        text = stringResource(R.string.Sign_up),
                        style = Typography.displayLarge.copy(color = primary100),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}


