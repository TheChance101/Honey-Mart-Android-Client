package org.the_chance.honeymart.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import org.the_chance.honeymart.ui.features.login.navigateToLogin
import org.the_chance.honeymart.ui.features.profile.navigateToProfileScreen
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
    val navController = LocalNavigationProvider.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                is MainEffect.OnClickProfileEffect -> {
                    navController.navigateToProfileScreen()
                }

                is MainEffect.OnClickLogoutEffect -> {
                    navController.navigateToLogin()
                }
            }
        }
    }
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

    Row {
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