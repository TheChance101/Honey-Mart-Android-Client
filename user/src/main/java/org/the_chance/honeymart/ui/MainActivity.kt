package org.the_chance.honeymart.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import org.the_chance.honeymart.domain.usecase.GetThemeUseCase
import org.the_chance.honeymart.ui.feature.bottom_navigation.BottomBarUi
import org.the_chance.honeymart.ui.feature.profile.ProfileScreen
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    @Inject
     lateinit var  getThemeState :GetThemeUseCase
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            CompositionLocalProvider(LocalNavigationProvider provides rememberNavController()) {
                HoneyMartTheme(darkTheme = runBlocking {getThemeState()?:false}) {
                    val bottomNavState = checkBottomBarState()
                    Scaffold(
                        bottomBar = {
                            BottomBarUi(bottomNavState)
                        },
                        contentWindowInsets = WindowInsets(0, 0, 0, 0)
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxSize()
                                .padding(innerPadding)
                        )
                        {
                            ProfileScreen()
                           // MainNavGraph()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun checkBottomBarState(): MutableState<Boolean> {
        val navController = LocalNavigationProvider.current
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

        val bottomBarScreens = listOf(
            Screen.HomeScreen.route,
            Screen.CategoryScreenWithArgs.route,
            Screen.ProductScreenWithArgs.route,
            Screen.CartScreen.route,
            Screen.OrderScreen.route,
            Screen.OrderDetailsScreenWithArgs.route,
            Screen.WishListScreen.route,
            Screen.HomeScreen.route,
        )
        when (navBackStackEntry?.destination?.route) {
            in bottomBarScreens -> {
                // Show BottomBar
                bottomBarState.value = true
            }

            else -> {
                // Hide BottomBar
                bottomBarState.value = false
            }
        }
        return bottomBarState
    }
}

