package org.the_chance.honeymart.ui.features.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.composables.HoneyAuthScaffold
import org.the_chance.honymart.ui.composables.HoneyAuthFooter
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.HoneyTextFieldPassword
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.owner.R

@Composable
fun SignupScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    SignupContent(listener = viewModel, state = state)

}

@Composable
fun SignupContent(
    state: SignupUiState,
    listener: SignupInteractionListener,
) {
    Loading(state = state.isLoading)
    HoneyAuthScaffold {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    end = MaterialTheme.dimens.space56,
                    bottom = MaterialTheme.dimens.space16,
                    top = MaterialTheme.dimens.space56
                ),
            verticalArrangement = Arrangement.Center,
        ) {

            HoneyAuthHeader(
                title = stringResource(R.string.sign_up),
                subTitle = stringResource(R.string.create_an_account_name_your_market),
                textColor = black60,
                modifier = Modifier
                    .padding(bottom = MaterialTheme.dimens.space24)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
            HoneyTextField(
                text = state.fullName,
                hint = stringResource(R.string.full_namee),
                iconPainter = painterResource(org.the_chance.design_system.R.drawable.ic_person),
                onValueChange = listener::onFullNameInputChange,
                errorMessage = when (state.fullNameState) {
                    ValidationState.BLANK_FULL_NAME -> "name cannot be blank"
                    ValidationState.INVALID_FULL_NAME -> "Invalid name"
                    else -> ""
                },
            )
            HoneyTextField(
                text = state.email,
                hint = stringResource(R.string.emaill),
                iconPainter = painterResource(org.the_chance.design_system.R.drawable.ic_email),
                onValueChange = listener::onEmailInputChange,
                errorMessage = when (state.emailState) {
                    ValidationState.BLANK_EMAIL -> "email cannot be blank"
                    ValidationState.INVALID_EMAIL -> "Invalid email"
                    else -> ""
                },
            )
            HoneyTextFieldPassword(
                text = state.password,
                hint = stringResource(R.string.passwordd),
                iconPainter = painterResource(org.the_chance.design_system.R.drawable.ic_password),
                onValueChange = listener::onPasswordInputChanged,
                errorMessage = when (state.passwordState) {
                    ValidationState.BLANK_PASSWORD -> "Password cannot be blank"
                    ValidationState.INVALID_PASSWORD -> "Invalid password"
                    ValidationState.INVALID_PASSWORD_LENGTH -> "Password must be at least 8 characters"
                    else -> ""
                },
            )
            HoneyTextFieldPassword(
                text = state.confirmPassword,
                hint = stringResource(R.string.confirm_passwordd),
                iconPainter = painterResource(org.the_chance.design_system.R.drawable.ic_password),
                onValueChange = listener::onConfirmPasswordChanged,
                errorMessage = when (state.confirmPasswordState) {
                    ValidationState.INVALID_CONFIRM_PASSWORD -> "Invalid confirm password"
                    else -> ""
                }
            )


            HoneyFilledButton(
                label = stringResource(R.string.continuo),
                onClick = listener::onClickSignup,
                background = primary100,
                contentColor = Color.White,
                modifier = Modifier.padding(
                    bottom = MaterialTheme.dimens.space24,
                    top = MaterialTheme.dimens.space32
                )
            )
            HoneyAuthFooter(
                text = stringResource(R.string.alrady_have_account),
                textButtonText = stringResource(R.string.log_in),
                onTextButtonClicked = listener::onClickLogin,
                modifier = Modifier.Companion.align(Alignment.CenterHorizontally)
            )

        }
    }

}


@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun signupPreview() {
    SignupScreen()
}