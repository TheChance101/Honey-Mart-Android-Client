package org.the_chance.honeymart.ui.features.login.waiting_approved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.components.WaitingApprovePlaceholder
import org.the_chance.honeymart.ui.features.signup.SignUpViewModel
import org.the_chance.honeymart.ui.features.signup.SignupUiState

@Composable
fun WaitingApproveScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    WaitingApproveContent(state = state)
}

@Composable
private fun WaitingApproveContent(
    state: SignupUiState
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Need approve from admin to complete create market",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )
        }
        WaitingApprovePlaceholder(state = true)
    }

}
