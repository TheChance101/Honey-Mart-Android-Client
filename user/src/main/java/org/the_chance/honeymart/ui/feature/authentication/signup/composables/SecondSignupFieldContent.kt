package org.the_chance.honeymart.ui.feature.authentication.signup.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.authentication.signup.SignupInteractionListener
import org.the_chance.honeymart.ui.feature.authentication.signup.SignupUiState
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextFieldPassword
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun SecondSignupFieldContent(state: SignupUiState, listener: SignupInteractionListener){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
    ) {
        HoneyTextFieldPassword(
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            text = state.passwordState.value,
            hint = stringResource(R.string.password),
            iconPainter = painterResource(id = R.drawable.ic_password),
            onValueChange = listener::onPasswordInputChanged,
            errorMessage = state.passwordState.errorState,
        )
        HoneyTextFieldPassword(
            text = state.confirmPasswordState.value,
            hint = stringResource(R.string.confirm_password),
            iconPainter = painterResource(id = R.drawable.ic_password),
            onValueChange = listener::onConfirmPasswordChanged,
            errorMessage = state.confirmPasswordState.errorState,
        )
        HoneyFilledButton(
            label = stringResource(id = R.string.sign_up),
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space40
            ),
            onClick = listener::onClickSignup,
            isLoading = state.isLoading
        )
    }
}