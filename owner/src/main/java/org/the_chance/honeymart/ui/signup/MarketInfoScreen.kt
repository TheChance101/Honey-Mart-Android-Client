package org.the_chance.honeymart.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Created by Aziza Helmy on 8/7/2023.
 */
@Composable
fun MarketInfoScreen(viewModel: SignUpViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()


}

@Composable
fun MarketInfoContent() {

}

@Preview(device = Devices.TABLET, showSystemUi = true)
@Composable
fun MarketInfoScreenPreview() {
    MarketInfoScreen()
}