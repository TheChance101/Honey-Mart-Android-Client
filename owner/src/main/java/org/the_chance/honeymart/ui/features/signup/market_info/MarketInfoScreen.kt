package org.the_chance.honeymart.ui.features.signup.market_info

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.HoneyAuthScaffold
import org.the_chance.honeymart.ui.features.signup.SignUpViewModel
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.theme.primary100


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
//        Column {
//            HoneyTextField(
//                text = state.marketName.value,
//                hint = stringResource(R.string.full_name),
//                iconPainter = painterResource(R.drawable.icon_shop),
//                onValueChange = listener::onMarketNameInputChange,
//                errorMessage = "",
//            )
//            HoneyTextField(
//                text = state.address.value,
//                hint = stringResource(R.string.address),
//                iconPainter = painterResource(R.drawable.icon_map_point),
//                onValueChange = listener::onMarketAddressInputChange,
//                errorMessage = "",
//            )
//            HoneyTextField(
//                text = state.description.value,
//                hint = stringResource(R.string.description),
//                iconPainter = painterResource(R.drawable.icon_document_add),
//                onValueChange = listener::onDescriptionInputChanged,
//                errorMessage = "",
//            )
//            Column(
//                modifier = Modifier.padding(MaterialTheme.dimens.space16)
//            ) {
//                Text(
//                    modifier = Modifier.padding(bottom = MaterialTheme.dimens.space8),
//                    text = stringResource(R.string.market_images),
//                    style = MaterialTheme.typography.displaySmall,
//                    color = if (state.marketImage.errorState.isNotEmpty()) error else black37,
//                    textAlign = TextAlign.Center,
//                )
//                Card(
//                    modifier = Modifier.size(102.dp),
//                    colors = CardDefaults.cardColors(white200),
//                    onClick = {},
//                    shape = MaterialTheme.shapes.medium
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            modifier = Modifier.size(MaterialTheme.dimens.icon24),
//                            painter = painterResource(R.drawable.icon_add_product),
//                            contentDescription = "Icon Add",
//                            tint = black60
//                        )
//                    }
//                }
//            }
//        }
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