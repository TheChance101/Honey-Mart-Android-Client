package org.the_chance.honeymart.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.login.navigateToLogin
import org.the_chance.honeymart.ui.navigation.LocalNavigationProvider
import org.the_chance.honeymart.ui.navigation.NavigationRailScreen
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white
import java.util.Locale

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
                    //TODO  Navigate to Profile
                }
                is MainEffect.OnClickLogoutEffect -> {
                    navController.navigateToLogin()
                }
            }
        }
    }
    MainContent(state, viewModel, currentDestination, navController)
}


@Composable
fun MainContent(
    state: MainUiState,
    listener: MainInteractionListener,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    val screens = listOf(
        NavigationRailScreen.Orders,
        NavigationRailScreen.Category,
    )
    val navigationRail = listOf(
        Screen.Category.route,
        Screen.Orders.route,
    )
    val showNavigationRail = currentDestination?.route in navigationRail

    AnimatedVisibility(
        visible = showNavigationRail,
        enter = slideInHorizontally { -it },
        exit = slideOutHorizontally { -it }
    ) {
        NavigationRail(
            containerColor = MaterialTheme.colorScheme.onTertiary,
            header = {
                if (state.ownerImageUrl.isNotEmpty()) {
                    ImageNetwork(
                        modifier = Modifier
                            .size(MaterialTheme.dimens.icon48)
                            .clip(CircleShape)
                            .clickable { listener.onClickProfile() },
                        imageUrl = state.ownerImageUrl,
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(MaterialTheme.dimens.icon48)
                            .clip(CircleShape)
                            .background(
                                MaterialTheme.colorScheme.primary,
                            )
                            .clickable { listener.onClickProfile() },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = state.ownerNameFirstCharacter.toString()
                                .uppercase(Locale.ROOT),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = white
                            )
                        )
                    }
                }
            }
        ) {
            Spacer(modifier = Modifier.weight(1f))
            screens.forEach { screen ->
                AppNavRailItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { listener.onClickLogout() }
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "Logout Icon",
                tint = black60
            )
        }
    }
}