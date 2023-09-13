package org.the_chance.honeymart.ui.features.notifications.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honeymart.ui.features.notifications.NotificationsUiState
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun NotificationCard(
    state: NotificationsUiState,
    modifier: Modifier = Modifier,
    imageUrl: String,
    userName  : String ,
    date :String ,
    orderState : String,
    onClickCard: (orderId: Long) -> Unit = {},
    isSelected: Boolean = false
) {
    val selectedColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary
        else Color.Transparent, label = "Selected color"
    )
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .height(MaterialTheme.dimens.itemOrder)
            .clickable { onClickCard(state.updatedNotifications[0].orderId) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Divider(
                modifier = Modifier
                    .width(10.dp)
                    .fillMaxHeight(),
                thickness = 100.dp,
                color = selectedColor
            )
            ImageNetwork(
                modifier = Modifier
                    .size(MaterialTheme.dimens.itemProductImage)
                    .clip(CircleShape),
                imageUrl = imageUrl
            )
            Column(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.dimens.space12,
                        bottom = MaterialTheme.dimens.space12
                    ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                )
                Text(
                    text = orderState,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                )
            }
            Column(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.dimens.space12,
                        bottom = MaterialTheme.dimens.space12,
                    ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = date ,
                    style = MaterialTheme.typography.bodyMedium
                        .copy(color = MaterialTheme.colorScheme.onBackground),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewNotificationCard() {
    NotificationCard(state = NotificationsUiState(),
        imageUrl = "https://i.pinimg.com/originals/0a/0b/9a/0a0b9a4b5b5b3b0b5b5b5b5b5b5b5b5b.jpg",
        userName ="Sara Salah ", date ="12:00", orderState = "Cancel the order",)

}