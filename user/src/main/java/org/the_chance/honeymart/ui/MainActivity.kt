package org.the_chance.honeymart.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.bottom_navigation.BottomBarUi
import org.the_chance.honeymart.ui.navigation.MainNavGraph
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // make your action here
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied.
            }
        }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            CompositionLocalProvider(LocalNavigationProvider provides rememberNavController()) {
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
    @Composable
    fun CheckNotificationPermission() {
        val openDialog = remember { mutableStateOf(false) }
        val permission = Manifest.permission.POST_NOTIFICATIONS
        when {
            ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                // make your action here
            }

            shouldShowRequestPermissionRationale(permission) -> {
                openDialog.value = true
                if (openDialog.value)
                    AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        text = {
                            Text(
                                style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
                                text = stringResource(R.string.notification_permission_required)
                            )
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                openDialog.value = false
                            }) { Text(text = stringResource(R.string.dismiss)) }
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                openAppSettings()
                                openDialog.value = false
                            }) { Text(text = stringResource(R.string.go_to_settings)) }
                        },
                    )
            }

            else -> {
                requestNotificationPermission.launch(permission)
            }
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}