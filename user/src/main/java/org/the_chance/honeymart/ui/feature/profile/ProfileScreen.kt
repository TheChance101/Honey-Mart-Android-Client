package org.the_chance.honeymart.ui.feature.profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.EmptyOrdersPlaceholder
import org.the_chance.honeymart.ui.feature.authentication.navigateToAuth
import org.the_chance.honeymart.ui.feature.home.navigateToHomeScreen
import org.the_chance.honeymart.ui.feature.notifications.navigateToNotificationsScreen
import org.the_chance.honeymart.ui.feature.orders.navigateToOrderScreen
import org.the_chance.honeymart.ui.feature.profile.composable.ProfileSuccessScreen
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.composables.Loading


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current


    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                is ProfileUiEffect.ClickMyOrderEffect -> navController.navigateToOrderScreen()
                is ProfileUiEffect.ClickNotificationEffect -> navController.navigateToNotificationsScreen()
                is ProfileUiEffect.ClickCouponsEffect -> {}
                is ProfileUiEffect.ClickLogoutEffect -> {
                    navController.navigateToHomeScreen()
                }

                ProfileUiEffect.UnAuthorizedUserEffect -> navController.navigateToAuth()
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getData()
    }

    ProfileContent(
        state = state,
        listener = viewModel,
    )
}

@Composable
private fun ProfileContent(
    state: ProfileUiState,
    listener: ProfileInteractionsListener,
) {
    val context = LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { handleImageSelection(it, context, listener::onImageSelected) }
    )

    AppBarScaffold {

        Loading(state = state.isLoading)

        ConnectionErrorPlaceholder(
            state = state.isConnectionError,
            onClickTryAgain = listener::getData
        )

        ContentVisibility(state.isShowDialog) {
            CustomAlertDialog(
                message = stringResource(R.string.dialog_title),
                onConfirm = {
                    listener.onClickLogout()
                },
                onCancel = { listener.resetDialogState() },
                onDismissRequest = { listener.resetDialogState() }
            )
        }

        EmptyOrdersPlaceholder(
            state = state.error is ErrorHandler.UnAuthorizedUser,
            image = R.drawable.placeholder_order,
            title = stringResource(R.string.you_are_not_logged_in),
            subtitle = stringResource(R.string.login_and_get_access_to_your_orders_wishlist_and_more),
            buttonLabel = stringResource(R.string.login),
            onClickDiscoverMarkets = listener::onClickLogin
        )
        ContentVisibility(state = state.showProfile()) {
            ProfileSuccessScreen(state, photoPickerLauncher, listener)
        }
    }
}


private fun handleImageSelection(
    uri: Uri?,
    context: Context,
    onImageSelected: (ByteArray) -> Unit
) {
    val imageByteArrays =
        uri?.let {
            context.contentResolver.openInputStream(it)?.use { inputStream ->
                inputStream.readBytes()
            }
        }
    imageByteArrays?.let { onImageSelected(it) }
}
