package org.the_chance.honeymart.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.design_system.R
import org.the_chance.honeymart.data.source.remote.network.ServiceLocator
import org.the_chance.honeymart.di.FCMNotificationImp
import org.the_chance.honeymart.ui.feature.bottom_navigation.BottomBarUi
import org.the_chance.honeymart.ui.navigation.MainNavGraph
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honymart.ui.composables.PermissionDialog
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.initialize(FCMNotificationImp(applicationContext))
        installSplashScreen()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            CompositionLocalProvider(LocalNavigationProvider provides rememberNavController()) {
                handleNotificationIntent(intent)
                HoneyMartTheme {
                    val bottomNavState = checkBottomBarState()
                    CheckNotificationPermission()
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
                        { MainNavGraph() }
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
            Screen.ProfileScreen.route,
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun CheckNotificationPermission() {
        val isDialogOpen = remember { mutableStateOf(true) }
        val permissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

        when {
            !permissionState.status.isGranted && !permissionState.status.shouldShowRationale -> {
                LaunchedEffect(Unit) {
                    permissionState.launchPermissionRequest()
                }
            }

            permissionState.status.shouldShowRationale && isDialogOpen.value -> {
                PermissionDialog(
                    onDismissRequest = { isDialogOpen.value = false },
                    message = stringResource(R.string.notification_permission_required),
                    onClickDismiss = { isDialogOpen.value = false },
                    onClickGoToSettings = {
                        isDialogOpen.value = false
                        openAppSettings()
                    }
                )
            }
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun handleNotificationIntent(intent: Intent?) {
        if (intent?.action == "OPEN_NOTIFICATIONS_SCREEN") {
//            val navController = LocalNavigationProvider.current
//            navController.navigateToNotificationsScreen()
        }
    }
}