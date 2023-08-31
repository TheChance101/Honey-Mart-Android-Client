package org.the_chance.honeymart.ui.features.authentication.signup.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.the_chance.honeymart.ui.components.HoneyAuthScaffold
import org.the_chance.honeymart.ui.features.authentication.signup.SignupInteractionListener
import org.the_chance.honeymart.ui.features.authentication.signup.SignupUiState
import org.the_chance.honymart.ui.composables.HoneyAuthFooter
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.HoneyTextFieldPassword
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.design_system.R

@Composable
fun OwnerFieldsScaffold(
    state: SignupUiState,
    listener: SignupInteractionListener,
){
    HoneyAuthScaffold(
        modifier = Modifier.imePadding()
    ) {
        HoneyAuthHeader(
            titleColor = MaterialTheme.colorScheme.onSecondary,
            subTitleColor = MaterialTheme.colorScheme.onBackground,
            title = stringResource(R.string.sign_up),
            subTitle = stringResource(R.string.create_an_account_name_your_market),
        )
        Column {
            HoneyTextField(
                text = state.fullNameState.value,
                hint = stringResource(R.string.full_name),
                iconPainter = painterResource(R.drawable.ic_person),
                onValueChange = listener::onFullNameInputChange,
                errorMessage = state.fullNameState.errorState,
            )
            HoneyTextField(
                text = state.emailState.value,
                hint = stringResource(R.string.email),
                iconPainter = painterResource(R.drawable.ic_email),
                onValueChange = listener::onEmailInputChange,
                errorMessage = state.emailState.errorState,
            )
            HoneyTextFieldPassword(
                text = state.passwordState.value,
                hint = stringResource(R.string.password),
                iconPainter = painterResource(R.drawable.ic_password),
                onValueChange = listener::onPasswordInputChanged,
                errorMessage = state.passwordState.errorState,
            )
            HoneyTextFieldPassword(
                text = state.confirmPasswordState.value,
                hint = stringResource(R.string.confirm_password),
                iconPainter = painterResource(R.drawable.ic_password),
                onValueChange = listener::onConfirmPasswordChanged,
                errorMessage = state.confirmPasswordState.errorState,
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HoneyFilledButton(
                label = stringResource(R.string.continue_word),
                onClick = listener::onClickContinue,
                background = primary100,
                contentColor = Color.White,
                isLoading = state.isLoading,
                isButtonEnabled = state.isButtonEnabled
            )

            HoneyAuthFooter(
                text = stringResource(R.string.already_have_account),
                textButtonText = stringResource(R.string.log_in),
                onTextButtonClicked = listener::onClickLogin,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}