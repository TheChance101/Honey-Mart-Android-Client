package org.the_chance.honeymart.ui.features.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(
) {

    LoginContent()
}

@Composable
fun LoginContent(
) {

}

@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}