package org.the_chance.honeymart.ui.feature.profile.composable

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.profile.ProfileInteractionsListener
import org.the_chance.honeymart.ui.feature.profile.ProfileUiState
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.nullColor

@Composable
fun ProfileSuccessScreen(
    state: ProfileUiState,
    listener: ProfileInteractionsListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                enabled = true,
                state = rememberScrollState()
            )
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
                contentScale = ContentScale.Crop,
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
                            listener.onClickCameraIcon()
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
            text = state.accountInfo.fullName,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.space16),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Text(
            text = state.accountInfo.email,
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
            iconId = R.drawable.ic_logout,
            title = "Logout",
            onClick = listener::showDialog,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))
    }
}