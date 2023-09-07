package org.the_chance.honeymart.ui.feature.authentication.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.NavigationHandler
import org.the_chance.honeymart.ui.feature.authentication.signup.navigateToSignupScreen
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.HoneyTextFieldPassword
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                LoginUiEffect.ClickLoginEffect -> {
                    navController.popBackStack(
                        Screen.AuthScreen.route, true
                    )
                }

                LoginUiEffect.ClickSignUpEffect -> {
                    navController.navigateToSignupScreen()
                }

                LoginUiEffect.ShowToastEffect -> {
                    Toast.makeText(
                        context,
                        state.validationToast.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    LoginContent(listener = viewModel, state = state)
}

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    listener: LoginInteractionListener,
    state: LoginUiState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
    ) {
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

                    HoneyAuthHeader(
                        titleColor = MaterialTheme.colorScheme.onPrimary,
                        title = stringResource(R.string.welcome_back),
                        subTitle = stringResource(
                            R.string.reconnect_with_your_favorite_brands_and_saved_items_log_in_today
                        ),
                        subTitleColor = white,
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.dimens.space24)
                            .align(
                                Alignment.CenterHorizontally
                            )
                    )
                }
            }
            HoneyTextField(
                oneLineOnly = true,
                text = state.emailState.value,
                hint = stringResource(R.string.email),
                iconPainter = painterResource(id = R.drawable.ic_email),
                onValueChange = listener::onEmailInputChange,
                errorMessage = state.emailState.errorState,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) },
                     onDone = { keyboardController?.hide() }
                )
            )
            HoneyTextFieldPassword(
                text = state.passwordState.value,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                hint = stringResource(R.string.password),
                iconPainter = painterResource(id = R.drawable.ic_password),
                onValueChange = listener::onPasswordInputChanged,
                errorMessage = state.passwordState.errorState,
            )
            HoneyFilledButton(
                label = stringResource(id = R.string.log_in),
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space40
                ),
                onClick = listener::onClickLogin,
                isLoading = state.isLoading
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


