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
import org.the_chance.honeymart.ui.components.NavigationHandler
import org.the_chance.honeymart.ui.features.authentication.login.navigateToLogin
import org.the_chance.honeymart.ui.features.profile.navigateToProfileScreen
import org.the_chance.honeymart.ui.navigation.Graph
import org.the_chance.honeymart.ui.navigation.LocalNavigationProvider
import org.the_chance.honeymart.ui.navigation.NavigationRail
import org.the_chance.honeymart.ui.navigation.NavigationRailScreen
import org.the_chance.honeymart.ui.navigation.RootNavigationGraph
import org.the_chance.honeymart.ui.navigation.Screen

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
                is MainEffect.OnClickProfileEffect -> {
                    navController.navigateToProfileScreen()
                }

                is MainEffect.OnClickLogoutEffect -> {
                    navController.popBackStack(Graph.MAIN_GRAPH, true)
                    navController.navigateToLogin()
                }

                is MainEffect.ShowLogoutErrorToastEffect -> {
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
    val screenRouts = listOf(
        NavigationRailScreen.Orders.route,
        NavigationRailScreen.Category.route,
        NavigationRailScreen.Coupons.route,
        Screen.Profile.route
    )
    val showNavigationRail = currentDestination?.route in screenRouts
    if (showNavigationRail) {
        listener.onGetOwnerInitials()
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