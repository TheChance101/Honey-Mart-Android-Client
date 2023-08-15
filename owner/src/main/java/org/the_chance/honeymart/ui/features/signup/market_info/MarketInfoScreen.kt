package org.the_chance.honeymart.ui.features.signup.market_info

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.composables.HoneyAuthScaffold
import org.the_chance.honeymart.ui.features.signup.SignUpViewModel
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.owner.R
import org.the_chance.honymart.ui.theme.error
import org.the_chance.honymart.ui.theme.white200


@Composable
fun MarketInfoScreen(viewModel: SignUpViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    MarketInfoContent(state = state.marketInfoUiState, listener = viewModel)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketInfoContent(
    state: MarketInfoUiState,
    listener: MarketInfoInteractionsListener,
) {
    HoneyAuthScaffold {
        HoneyAuthHeader(
            title = stringResource(R.string.market_info),
            subTitle = stringResource(R.string.create_an_account_name_your_market),
        )
        Column {
            HoneyTextField(
                text = state.marketName.value,
                hint = stringResource(R.string.full_name),
                iconPainter = painterResource(R.drawable.icon_shop),
                onValueChange = listener::onMarketNameInputChange,
                errorMessage = "",
            )
            HoneyTextField(
                text = state.address.value,
                hint = stringResource(R.string.address),
                iconPainter = painterResource(R.drawable.icon_map_point),
                onValueChange = listener::onMarketAddressInputChange,
                errorMessage = "",
            )
            HoneyTextField(
                text = state.description.value,
                hint = stringResource(R.string.description),
                iconPainter = painterResource(R.drawable.icon_document_add),
                onValueChange = listener::onDescriptionInputChanged,
                errorMessage = "",
            )
            Column(
                modifier = Modifier.padding(MaterialTheme.dimens.space16)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = MaterialTheme.dimens.space8),
                    text = stringResource(R.string.market_images),
                    style = MaterialTheme.typography.displaySmall,
                    color = if (state.marketImage.errorState.isNotEmpty()) error else black37,
                    textAlign = TextAlign.Center,
                )
                Card(
                    modifier = Modifier.size(102.dp),
                    colors = CardDefaults.cardColors(white200),
                    onClick = {},
                    shape = MaterialTheme.shapes.medium
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(MaterialTheme.dimens.icon24),
                            painter = painterResource(org.the_chance.design_system.R.drawable.icon_add_product),
                            contentDescription = "Icon Add",
                            tint = black60
                        )
                    }
                }
            }
        }
        HoneyFilledButton(
            label = stringResource(R.string.send),
            onClick = listener::onClickSendButton,
            background = primary100,
            contentColor = Color.White,
            isLoading = state.isLoading
        )
    }
}

@Preview(device = Devices.TABLET, showSystemUi = true)
@Composable
fun MarketInfoScreenPreview() {
    MarketInfoScreen()
}