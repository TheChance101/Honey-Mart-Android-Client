package org.the_chance.honeymart.ui.feature.profile

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
import androidx.compose.ui.res.painterResource
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
import org.the_chance.honeymart.ui.feature.market.navigateToMarketScreen
import org.the_chance.honeymart.ui.feature.order_details.navigateToOrderDetailsScreen
import org.the_chance.honeymart.ui.feature.orders.OrderUiEffect
import org.the_chance.honeymart.ui.feature.orders.navigateToOrderScreen
import org.the_chance.honeymart.ui.feature.profile.composable.NavCard


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
                is ProfileUiEffect.ClickNotificationEffect ->{} //navController.navigateToNotificationScreen()
                is ProfileUiEffect.ClickCouponsEffect ->{} //navController.navigateToCouponsScreen()
                is ProfileUiEffect.ClickLogoutEffect -> navController.navigateToLogin()
                ProfileUiEffect.ClickThemeEffect -> {}
                ProfileUiEffect.ShowDialogEffect -> {}
                ProfileUiEffect.ShowToastEffect -> {}
            }
        }
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

    AppBarScaffold {

        Loading(state = state.isLoading)

        ConnectionErrorPlaceholder(
            state = state.isConnectionError,
            onClickTryAgain = listener::getData
        )

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
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillBounds,
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "",
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                        .size(MaterialTheme.dimens.space36)
                        .clip(CircleShape)
                        .clickable { listener::updateImage }
                        .border(
                            width = MaterialTheme.dimens.space2,
                            color = MaterialTheme.colorScheme.onTertiary,
                            shape = CircleShape
                        )
                        .align(Alignment.BottomEnd),
                    tint = MaterialTheme.colorScheme.onTertiary,
                )
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

            NavCard(iconId = R.drawable.ic_bill_list, title = "My Order" , onClick = listener::onClickMyOrder)

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))

            NavCard(iconId = R.drawable.ic_coupons, title = "Coupons" , onClick = listener::onClickCoupons)
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))

            NavCard(iconId = R.drawable.ic_notification, title = "Notification" , onClick = listener::onClickNotification)

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))

            NavCard(iconId = R.drawable.ic_sun, title = "Theme" , onClick = listener::onClickTheme)

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))

            NavCard(iconId = R.drawable.ic_logout, title = "Logout" , onClick = listener::onClickLogout , color = MaterialTheme.colorScheme.error)

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))
        }
    }
}