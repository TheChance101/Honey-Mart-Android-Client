package org.the_chance.honeymart.ui.features.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honymart.ui.composables.Loading

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    NotificationsContent(state)
}

@Composable
fun NotificationsContent(
state: NotificationsUiState ,
) {
    Loading(state = true)
    
}