package org.the_chance.honeymart.ui.features.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.navigation.LocalNavigationProvider
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.components.HoneyAuthScaffold
import org.the_chance.honeymart.ui.features.category.navigateToCategoryScreen
import org.the_chance.honeymart.ui.features.login.navigateToLogin
import org.the_chance.honeymart.ui.features.login.waiting_approved.navigateToWaitingApproveScreen
import org.the_chance.honeymart.ui.features.signup.composables.OwnerFieldsScaffold
import org.the_chance.honeymart.ui.features.signup.market_info.MarketInfoScreen
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.owner.R

@Composable
fun SignupScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavigationProvider.current
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                SignupUiEffect.ShowValidationToast -> {
                    Toast.makeText(
                        context,
                        state.validationToast.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                SignupUiEffect.ClickLoginEffect -> {
                    navController.navigateToLogin()
                }
                SignupUiEffect.NavigateToCategoriesEffect -> {
                    navController.navigateToCategoryScreen()
                }
                SignupUiEffect.NavigateToWaitingApproveEffect -> {
                    navController.navigateToWaitingApproveScreen()
                }
            }
        }
    }
    SignupContent(listener = viewModel, state = state)
}

@Composable
fun SignupContent(
    state: SignupUiState,
    listener: SignupInteractionListener,
) {

    ContentVisibility(state = !state.isOwnerAccountCreated) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = MaterialTheme.dimens.space32),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            HoneyAuthScaffold(
                modifier = Modifier.imePadding()
            ) {
                HoneyAuthHeader(
                    title = stringResource(R.string.sign_up),
                    subTitle = stringResource(R.string.create_an_account_name_your_market),
                )
                OwnerFieldsScaffold(state = state, listener = listener)
            }
        }
    }

    ContentVisibility(state = state.isOwnerAccountCreated) {
        MarketInfoScreen()
    }
}


@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun PreviewSignupScreen() {
    SignupScreen()
}