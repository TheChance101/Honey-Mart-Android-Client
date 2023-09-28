package org.the_chance.honeymart.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.design_system.R
import androidx.compose.runtime.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import org.the_chance.honeymart.data.source.remote.network.ServiceLocator
import org.the_chance.honeymart.di.FCMNotificationImp
import org.the_chance.honeymart.ui.main.MainScreen
import org.the_chance.honeymart.ui.navigation.LocalNavigationProvider
import org.the_chance.honymart.ui.composables.PermissionDialog
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.initialize(FCMNotificationImp(applicationContext))
        installSplashScreen()
        setContent {
            CompositionLocalProvider(LocalNavigationProvider provides rememberNavController()) {
                HoneyMartTheme {
                    CheckNotificationPermission()
                    MainScreen()
                }
            }
        }

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
}

