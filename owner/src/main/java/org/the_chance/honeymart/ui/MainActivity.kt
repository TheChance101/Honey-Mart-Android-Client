package org.the_chance.honeymart.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.LocalNavigationProvider
import org.the_chance.honeymart.ui.features.category.CategoriesScreen
import org.the_chance.honeymart.ui.features.products.ProductsScreen
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalNavigationProvider provides rememberNavController()) {
                HoneyMartTheme {
                    val navigationRailState = checkNavigationRailState()
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                    )
                    {
//                        AnimatedVisibility(
//                            visible = navigationRailState.value,
//                            enter = slideInHorizontally(initialOffsetX = { -it }),
//                            exit = slideOutHorizontally(targetOffsetX = { -it }),
//                        ) {
//                            NavigationRail()
//                        }
//                        MainNavGraph()
                        ProductsScreen()

//                        CategoriesScreen()
                    }
                }
            }
        }
    }

    @Composable
    private fun checkNavigationRailState(): MutableState<Boolean> {
        val navController = LocalNavigationProvider.current
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val navigationRailState = rememberSaveable { (mutableStateOf(false)) }

        val navigationRailScreens = listOf(
            Screen.Category.route,
            Screen.Orders.route,
        )
        when (navBackStackEntry?.destination?.route) {
            in navigationRailScreens -> {
                navigationRailState.value = true
            }

            else -> {
                navigationRailState.value = false
            }
        }
        return navigationRailState
    }
}