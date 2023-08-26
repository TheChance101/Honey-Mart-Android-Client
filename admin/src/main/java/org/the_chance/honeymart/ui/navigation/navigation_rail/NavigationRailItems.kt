package org.the_chance.honeymart.ui.navigation.navigation_rail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.the_chance.honeymart.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.features.login.navigateToLogin
import org.the_chance.honeymart.ui.features.requests.MarketsUiEffect
import org.the_chance.honeymart.ui.features.requests.MarketsViewModel
import org.the_chance.honeymart.ui.features.requests.contentScreen
import org.the_chance.honeymart.ui.navigation.NavigationScreen
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white
import java.util.Locale

@Composable
fun NavigationRail(
    viewModel: MarketsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                is MarketsUiEffect.OnClickLogoutEffect -> {
                    navController.navigateToLogin()
                }
                else -> {}
            }
        }
    }
    val screens = listOf(
        NavigationScreen.Requests,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    ContentVisibility(state = state.contentScreen()) {
        NavigationRail(
            containerColor = MaterialTheme.colorScheme.onTertiary,
            header = {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.icon48)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.primary,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = state.adminName.toString().uppercase(Locale.ROOT),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = white
                        )
                    )
                }
            }
        ) {
            Spacer(modifier = Modifier.weight(1f))
            screens.forEach { screen ->
                NavRailItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { viewModel.onClickLogout() }
                    .padding(16.dp),
                painter = painterResource(id = org.the_chance.design_system.R.drawable.ic_logout),
                contentDescription = "Logout Icon",
                tint = black60
            )
        }
    }
}

@Composable
fun NavRailItem(
    screen: NavigationScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .padding(MaterialTheme.dimens.space8)
            .width(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(MaterialTheme.dimens.space56)
                    .background(
                        color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.icon32)
                        .align(Alignment.Center),
                    painter = painterResource(id = screen.selectedIcon),
                    contentDescription = "",
                    tint = if (selected) white else black60,
                )
            }

            AnimatedVisibility(
                visible = selected,
            ) {
                Text(
                    modifier = Modifier.padding(top = MaterialTheme.dimens.space8),
                    text = screen.label,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    }
}