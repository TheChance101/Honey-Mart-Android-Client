package org.the_chance.honeymart.ui.feature.notifications.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.black87
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white
import org.the_chance.honymart.ui.theme.white200

@Composable
fun NotificationCard(
    modifier: Modifier = Modifier,
    painter: Painter,
    title: String,
    date: String,
    message: String
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(white)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(
                size = MaterialTheme.dimens.space40,
                onClick = { },
                backgroundColor = Color.Transparent
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painter,
                    contentDescription = "",
                    tint = black60
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = title,
                    color = black87
                )
                Text(
                    text = date,
                    color = black60
                )
                Text(
                    text = message,
                    color = black60
                )
            }
        }
        Divider (
            color = white200,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun NotificationSuccessCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(white)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(
                size = MaterialTheme.dimens.space40,
                onClick = { },
                backgroundColor = Color.Transparent
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.icon_order_nav),
                    contentDescription = "",
                    tint = black60
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "Order Success",
                    color = black87
                )
                Text(
                    text = "12:30",
                    color = black60
                )
                Text(
                    text = "Order #2453 has been success.\n" +
                            "Please wait for the product to be sent",
                    color = black60
                )
            }
        }
        Divider (
            color = white200,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun NotificationArrivedCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(white)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(
                size = MaterialTheme.dimens.space40,
                onClick = { },
                backgroundColor = Color.Transparent
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_delivery),
                    contentDescription = "",
                    tint = black60
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "Order Arrived",
                    color = black87,
                    style = Typography.displaySmall
                )
                Text(
                    text = "1 Aug, 2023",
                    color = black60,
                    style = Typography.displaySmall
                )
                Text(
                    text = "Order #46567 has been Completed &\n" +
                            "and arrived at the destination address.",
                    color = black60,
                    style = Typography.displaySmall
                )
            }
        }
        Divider (
            color = white200,
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
fun NotificationCardPreview() {
    Column(
        modifier = Modifier.background(white)
    ) {
        NotificationSuccessCard()
        NotificationArrivedCard()
    }
}