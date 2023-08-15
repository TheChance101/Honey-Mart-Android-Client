package org.the_chance.honeymart.ui.features.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import org.the_chance.honeymart.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.HoneyAuthScaffold
import org.the_chance.honymart.ui.composables.HoneyAuthFooter
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.HoneyTextFieldPassword
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
    val context = LocalContext.current
    val navController = LocalNavigationProvider.current

    HoneyAuthScaffold(
        modifier = Modifier.imePadding()
    ) {
        HoneyAuthHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.dimens.space64),
            title = stringResource(org.the_chance.design_system.R.string.welcome_back),
            subTitle = stringResource(R.string.login_to_discover_a_curated_selection_of_products_just_for_you),
        )
        Column {
            HoneyTextField(
                text = state.emailState.value,
                hint = stringResource(org.the_chance.design_system.R.string.email),
                iconPainter = painterResource(id = org.the_chance.design_system.R.drawable.ic_email),
                onValueChange = listener::onEmailInputChange,
                errorMessage = state.emailState.errorState
            )

            HoneyTextFieldPassword(
                text = state.passwordState.value,
                hint = stringResource(org.the_chance.design_system.R.string.password),
                iconPainter = painterResource(id = org.the_chance.design_system.R.drawable.ic_password),
                onValueChange = listener::onPasswordInputChanged,
                errorMessage = state.passwordState.errorState,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            )
        }

        HoneyFilledButton(
            label = stringResource(id = org.the_chance.design_system.R.string.log_in),
            onClick = listener::onClickLogin,
            isLoading = state.isLoading,
        )

        HoneyAuthFooter(
            text = stringResource(org.the_chance.design_system.R.string.don_t_have_an_account),
            textButtonText = stringResource(org.the_chance.design_system.R.string.Sign_up),
            onTextButtonClicked = listener::onClickSignup,
            modifier = Modifier.Companion.align(Alignment.CenterHorizontally)
        )
    }
}


@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}