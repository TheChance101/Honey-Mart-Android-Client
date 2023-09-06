package org.the_chance.honeymart.ui.feature.authentication.signup.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import org.the_chance.honeymart.ui.feature.authentication.signup.invalidUserAlreadyExists
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun FirstSignupFiledContent(
    state: SignupUiState,
    listener: SignupInteractionListener,
    pagerState: PagerState
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
    ) {
        HoneyTextField(
            oneLineOnly = true,
            text = state.fullNameState.value,
            hint = stringResource(R.string.full_name),
            iconPainter = painterResource(id = R.drawable.ic_person),
            onValueChange = listener::onFullNameInputChange,
            errorMessage = state.fullNameState.errorState,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
        )
        HoneyTextField(
            oneLineOnly = true,
            text = state.emailState.value,
            hint = stringResource(R.string.email),
            iconPainter = painterResource(id = R.drawable.ic_email),
            onValueChange = listener::onEmailInputChange,
            errorMessage = state.emailState.errorState,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() })
        )
        val coroutineScope = rememberCoroutineScope()
        HoneyFilledButton(
            label = stringResource(id = R.string.Continue),
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space40
            ),
            onClick = {
                listener.onClickContinue()
                if (!state.invalidUserAlreadyExists()) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            },
            isLoading = state.isLoading
        )
    }
}