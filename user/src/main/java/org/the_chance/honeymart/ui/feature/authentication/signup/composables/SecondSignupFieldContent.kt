package org.the_chance.honeymart.ui.feature.authentication.signup.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.launch
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.authentication.signup.SignupInteractionListener
import org.the_chance.honeymart.ui.feature.authentication.signup.SignupUiState
import org.the_chance.honeymart.ui.feature.authentication.signup.correctValidationFullNameAndEmail
import org.the_chance.honeymart.ui.feature.authentication.signup.invalidUserAlreadyExists
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextFieldPassword
import org.the_chance.honymart.ui.theme.dimens

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun SecondSignupFieldContent(
    state: SignupUiState,
    listener: SignupInteractionListener,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    if (state.invalidUserAlreadyExists()) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(0)
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
    ) {
        HoneyTextFieldPassword(
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
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
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
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