package org.the_chance.honeymart.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.profile.content.MarketInfoContent
import org.the_chance.honeymart.ui.features.profile.content.PersonalInfoContent
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.design_system.R

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ProfileContent(state = state, listener = viewModel)
}

@Composable
fun ProfileContent(
    state: ProfileUiState,
    listener: ProfileInteractionListener
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        HoneyMartTitle()
        Loading(state = state.isLoading)
        ContentVisibility(state = state.showContent()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    MarketInfoContent(state = state.marketInfo, listener = listener)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    PersonalInfoContent(state = state.personalInfo)
                }
            }
        }
    }

    ContentVisibility(state = state.showMarketStatusDialog) {
        CustomAlertDialog(
            message = stringResource(R.string.you_want_to_change_market_status) +
                    stringResource(R.string.are_you_sure),
            onConfirm = {
                listener.updateMarketStatus(status = state.marketInfo.status.state)
            },
            onCancel = { listener.dismessStatusDialog() },
            onDismissRequest = { listener.dismessStatusDialog() }
        )
    }
}


@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
private fun PreviewProfileScreen() {
    HoneyMartTheme {
        ProfileScreen()
    }
}