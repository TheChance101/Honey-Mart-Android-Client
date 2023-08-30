package org.the_chance.honeymart.ui.features.profile.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.honeymart.ui.features.profile.PersonalInfoUiState

@Composable
fun PersonalInfoContent(
    state: PersonalInfoUiState,
    modifier: Modifier = Modifier
) {

}

@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
private fun PreviewPersonalInfoContent() {
    PersonalInfoContent(state = PersonalInfoUiState())
}