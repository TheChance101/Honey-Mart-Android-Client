package org.the_chance.honeymart.ui.main

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import org.the_chance.honeymart.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.NavigationHandler
import org.the_chance.honeymart.ui.features.login.navigateToLogin
import org.the_chance.honeymart.ui.navigation.NavigationRail
import org.the_chance.honeymart.ui.navigation.NavigationRailScreen
import org.the_chance.honeymart.ui.navigation.RootNavigationGraph

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavigationProvider.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                MainEffect.OnClickLogoutEffect -> {
                    navController.navigateToLogin()
                }

                MainEffect.ShowLogoutErrorToastEffect -> {
                    Toast.makeText(
                        context,
                        state.validationToast.messageErrorLogout,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    MainContent(state, viewModel, currentDestination)
}

@Composable
fun MainContent(
    state: MainUiState,
    listener: MainInteractionListener,
    currentDestination: NavDestination?,
) {
    val screenRouts = listOf(NavigationRailScreen.Markets.route)
    val showNavigationRail = currentDestination?.route in screenRouts
    if (showNavigationRail) {
        listener.onGetAdminInitials()
    }
    Row(
        modifier = Modifier.navigationBarsPadding()
    ) {
        AnimatedVisibility(
            visible = showNavigationRail,
            enter = slideInHorizontally { -it },
            exit = slideOutHorizontally { -it }
        ) {
            NavigationRail(
                state,
                listener
            )
        }
        RootNavigationGraph()
    }
}
