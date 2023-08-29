package org.the_chance.honeymart.ui.features.signup.market_info


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import org.the_chance.honeymart.ui.features.signup.SignUpViewModel
import org.the_chance.honeymart.ui.features.signup.market_info.composables.MarketFieldsScaffold
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.owner.R


@Composable
fun MarketInfoScreen(viewModel: SignUpViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    MarketInfoContent(state = state.marketInfoUiState, listener = viewModel)
}

@Composable
fun MarketInfoContent(
    state: MarketInfoUiState,
    listener: MarketInfoInteractionsListener,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(end = MaterialTheme.dimens.space32),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HoneyAuthHeader(
            title = stringResource(R.string.market_info),
            subTitle = stringResource(R.string.create_an_account_name_your_market),
        )
        MarketFieldsScaffold(state = state, listener = listener)
    }
}

@Preview(device = Devices.TABLET, showSystemUi = true)
@Composable
fun MarketInfoScreenPreview() {
    MarketInfoScreen()
}