package org.the_chance.honeymart.ui.feature.bottom_navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun BottomBar(bottomNavState: MutableState<Boolean>) {
    val navController = LocalNavigationProvider.current

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Cart,
        BottomBarScreen.Order,
        BottomBarScreen.WishList,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AnimatedVisibility(
        visible = bottomNavState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            NavigationBar(
                containerColor = white,
            ) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    )
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val selected =
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    NavigationBarItem(
        icon = {
            Icon(
                painter =
                if (selected) painterResource(id = screen.selectedIcon)
                else painterResource(id = screen.unSelectedIcon),
                contentDescription = "Navigation icon"
            )
        },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                Log.e(
                    "AddItem",
                    "Current Start Destination: ${navController.graph.findStartDestination().route}",
                )
                Log.e("AddItem", "Current Destination: ${screen.route}")
                Log.e("AddItem", "Current Graph: ${navController.graph}")
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            when (screen) {
                BottomBarScreen.Home -> {
                    navController.popBackStack(Screen.MarketScreen.route, false)
                }

                BottomBarScreen.Cart -> {
                    navController.popBackStack(Screen.CartScreen.route, false)
                }

                BottomBarScreen.Order -> {
                    navController.popBackStack(Screen.OrderScreen.route, false)
                }

                BottomBarScreen.WishList -> {
                    navController.popBackStack(Screen.WishListScreen.route, false)
                }
            }

        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = white,
            indicatorColor = primary100,
            unselectedIconColor = black60
        )
    )
}