package org.the_chance.honeymart.ui.feature.authentication.signup.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.authentication.signup.SignupInteractionListener
import org.the_chance.honeymart.ui.feature.authentication.signup.SignupUiState
import org.the_chance.honeymart.ui.feature.authentication.signup.correctValidationFullNameAndEmail
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FirstSignupFiledContent(
    state: SignupUiState,
    listener: SignupInteractionListener,
    pagerState: PagerState
) {
    Column(
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
        )
        HoneyTextField(
            oneLineOnly = true,
            text = state.emailState.value,
            hint = stringResource(R.string.email),
            iconPainter = painterResource(id = R.drawable.ic_email),
            onValueChange = listener::onEmailInputChange,
            errorMessage = state.emailState.errorState,
        )
        val coroutineScope = rememberCoroutineScope()
        HoneyFilledButton(
            label = stringResource(id = R.string.Continue),
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space40
            ),
            onClick = {
                if (state.correctValidationFullNameAndEmail()) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                } else {
                     listener.onClickContinue()
                }
            },
            isLoading = state.isLoading
        )
    }
}