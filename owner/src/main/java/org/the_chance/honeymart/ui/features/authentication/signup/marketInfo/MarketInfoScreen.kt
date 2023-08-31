package org.the_chance.honeymart.ui.features.authentication.signup.marketInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.components.HoneyAuthScaffold
import org.the_chance.honeymart.ui.components.NavigationHandler
import org.the_chance.honeymart.ui.features.authentication.login.navigateToLogin
import org.the_chance.honeymart.ui.features.authentication.signup.SignUpViewModel
import org.the_chance.honeymart.ui.features.authentication.signup.SignupUiEffect
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.composables.MarketFieldsScaffold
import org.the_chance.honeymart.ui.features.authentication.waitingApprove.navigateToWaitingApproveScreen
import org.the_chance.honeymart.ui.features.category.navigateToCategoryScreen
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.owner.R

@Composable
fun MarketInfoScreen(viewModel: SignUpViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                SignupUiEffect.ClickLoginEffect -> {
                    navController.navigateToLogin()
                }

                SignupUiEffect.NavigateToCategoriesEffect -> {
                    navController.navigateToCategoryScreen()
                }

                SignupUiEffect.NavigateToWaitingApproveEffect -> {
                    navController.navigateToWaitingApproveScreen()
                }

                else -> {}
            }
        })
    MarketInfoContent(state = state.marketInfoUiState, listener = viewModel)
}

@Composable
fun MarketInfoContent(
    state: MarketInfoUiState,
    listener: MarketInfoInteractionsListener,
) {
    HoneyAuthScaffold(
        modifier = Modifier.imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = MaterialTheme.dimens.space32),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            HoneyAuthHeader(
                titleColor = MaterialTheme.colorScheme.onSecondary,
                subTitleColor = MaterialTheme.colorScheme.onBackground,
                title = stringResource(R.string.market_info),
                subTitle = stringResource(R.string.create_an_account_name_your_market),
            )
            MarketFieldsScaffold(state = state, listener = listener)
        }
    }
}

@Preview(device = Devices.TABLET, showSystemUi = true)
@Composable
fun MarketInfoScreenPreview() {
    MarketInfoScreen()
}