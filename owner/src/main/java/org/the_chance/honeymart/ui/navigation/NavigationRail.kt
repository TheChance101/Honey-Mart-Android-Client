package org.the_chance.honeymart.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.the_chance.honeymart.LocalNavigationProvider
import org.the_chance.honeymart.ui.features.login.navigateToLogin
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.white

@Composable
fun NavigationRail() {
    val navController = LocalNavigationProvider.current

    val screens = listOf(
        NavigationRailScreen.Orders,
        NavigationRailScreen.Category,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.onTertiary,
        header = {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { }
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "H",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        textAlign = TextAlign.Center,
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
                .padding(16.dp)
                .clickable { navController.navigateToLogin() },
            painter = painterResource(id = org.the_chance.design_system.R.drawable.ic_logout),
            contentDescription = "Logout Icon",
            tint = black60
        )
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavRailItem(
    screen: NavigationRailScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .padding(8.dp)
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
                    .size(56.dp)
                    .background(
                        color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = screen.selectedIcon),
                    contentDescription = "",
                    tint = if (selected) white else black60,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(
                visible = selected,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                Text(
                    text = screen.label,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSc() {
    NavigationRail()
}