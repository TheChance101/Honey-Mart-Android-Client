package org.the_chance.honeymart.ui.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.owner.R


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState()

    LoginContent(listener = viewModel, state = state)
}

@Composable
fun LoginContent(
    listener: LoginInteractionListener,
    state: LoginUiState,
) {
    Loading(state.isLoading)
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
                title = stringResource(org.the_chance.design_system.R.string.welcome_back),
                subTitle = stringResource(R.string.login_to_discover_a_curated_selection_of_products_just_for_you),
                textColor = black60,
                modifier = Modifier
                    .padding(bottom = MaterialTheme.dimens.space24)
                    .align(
                        Alignment.CenterHorizontally
                    )
            )
            HoneyTextField(
                text = state.email,
                hint = stringResource(org.the_chance.design_system.R.string.email),
                iconPainter = painterResource(id = org.the_chance.design_system.R.drawable.ic_email),
                onValueChange = listener::onEmailInputChange,
                errorMessage = when (state.emailState) {
                    ValidationState.BLANK_EMAIL -> "email cannot be blank"
                    ValidationState.INVALID_EMAIL -> "Invalid email"
                    else -> ""
                },
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))
            HoneyTextField(
                text = state.password,
                hint = stringResource(org.the_chance.design_system.R.string.password),
                iconPainter = painterResource(id = org.the_chance.design_system.R.drawable.ic_password),
                onValueChange = listener::onPasswordInputChanged,
                errorMessage = when (state.passwordState) {
                    ValidationState.BLANK_PASSWORD -> "Password cannot be blank"
                    ValidationState.INVALID_PASSWORD -> "Invalid password"
                    ValidationState.INVALID_PASSWORD_LENGTH -> "Password must be at least 8 characters"
                    else -> ""
                },
            )
            HoneyFilledButton(
                label = stringResource(id = org.the_chance.design_system.R.string.log_in),
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.dimens.space16,
                    vertical = MaterialTheme.dimens.space40
                ),
                onClick = listener::onClickLogin,
            )

            Spacer(modifier = Modifier.weight(1f))

            HoneyAuthFooter(
                text = stringResource(org.the_chance.design_system.R.string.don_t_have_an_account),
                textButtonText = stringResource(org.the_chance.design_system.R.string.Sign_up),
                onTextButtonClicked = listener::onClickSignup,
                modifier = Modifier.Companion.align(Alignment.CenterHorizontally)
            )

        }
    }
}

@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}