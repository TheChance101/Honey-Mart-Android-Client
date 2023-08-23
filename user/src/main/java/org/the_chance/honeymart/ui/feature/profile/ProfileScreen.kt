package org.the_chance.honeymart.ui.feature.profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.composables.AppBarScaffold
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.login.navigateToLogin
import org.the_chance.honeymart.ui.feature.profile.composable.NavCard
import org.the_chance.honymart.ui.composables.CustomAlertDialog
import org.the_chance.honymart.ui.theme.nullColor


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavigationProvider.current


    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                is ProfileUiEffect.ClickMyOrderEffect -> {} // navController.navigateToOrderScreen()
                is ProfileUiEffect.ClickNotificationEffect -> {} //navController.navigateToNotificationScreen()
                is ProfileUiEffect.ClickCouponsEffect -> {} //navController.navigateToCouponsScreen()
                is ProfileUiEffect.ClickLogoutEffect -> {
                    navController.navigateToLogin()
                }

                ProfileUiEffect.ClickThemeEffect -> viewModel.onClickThemeState(state.isDark)
                ProfileUiEffect.ShowDialogEffect -> {}
                ProfileUiEffect.ShowToastEffect -> {}
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
            state = state.isError,
            onClickTryAgain = listener::getData
        )

        if (state.isShowDialog) {
            CustomAlertDialog(
                message = stringResource(R.string.dialog_title),
                onConfirm = {
                    listener.onClickLogout()
                },
                onCancel = { listener.resetDialogState() },
                onDismissRequest = { listener.resetDialogState() }
            )
        }

        if (!state.isError && !state.isLoading)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MaterialTheme.dimens.space16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(state.accountInfo.profileImage),
                        contentDescription = "",
                        modifier = Modifier
                            .size(MaterialTheme.dimens.sizeProfileImage)
                            .fillMaxSize()
                            .clip(CircleShape)
                            .border(
                                width = MaterialTheme.dimens.space6,
                                color = MaterialTheme.colorScheme.onTertiary,
                                shape = CircleShape
                            )
                            .align(Alignment.Center)
                            .background(
                                color = if (state.accountInfo.profileImage == "") MaterialTheme.colorScheme.onTertiary else nullColor,
                                shape = CircleShape
                            ),
                        contentScale = ContentScale.FillBounds,
                    )

                    Box(
                        modifier = Modifier
                            .size(MaterialTheme.dimens.space48)
                            .align(Alignment.BottomEnd)
                            .padding(MaterialTheme.dimens.space4)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .border(
                                width = MaterialTheme.dimens.space2,
                                color = MaterialTheme.colorScheme.onTertiary,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_camera),
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(MaterialTheme.dimens.space24)
                                .clickable {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                    state.image?.let {
                                        listener.updateImage(image = it)
                                    }
                                }
                                .align(Alignment.Center),
                            tint = MaterialTheme.colorScheme.onTertiary,
                        )
                    }

                }

                Text(
                    text = "${state.accountInfo.fullName}",
                    modifier = Modifier
                        .padding(top = MaterialTheme.dimens.space16),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "${state.accountInfo.email}",
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.dimens.space4,
                            bottom = MaterialTheme.dimens.space32
                        ),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center,
                )

                NavCard(
                    iconId = R.drawable.ic_bill_list,
                    title = "My Order",
                    onClick = listener::onClickMyOrder
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))

                NavCard(
                    iconId = R.drawable.ic_coupons,
                    title = "Coupons",
                    onClick = listener::onClickCoupons
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))

                NavCard(
                    iconId = R.drawable.ic_notification,
                    title = "Notification",
                    onClick = listener::onClickNotification
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))

                NavCard(
                    iconId = R.drawable.ic_sun,
                    title = "Theme",
                    onClick = listener::onClickTheme
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))

                NavCard(
                    iconId = R.drawable.ic_logout,
                    title = "Logout",
                    onClick = listener::showDialog,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))
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
