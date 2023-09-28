package org.the_chance.honeymart.ui.feature.notifications.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white200

@Composable
fun NotificationCard(
    modifier: Modifier = Modifier,
    index: Int,
    painter: Painter,
    title: String,
    date: String,
    message: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(MaterialTheme.dimens.space16)
        ) {
            Icon(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.space8)
                    .size(MaterialTheme.dimens.icon24),
                painter = painter,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4),
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = date,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        if (index != 0) {
            Divider(
                color = white200,
                modifier = Modifier
                    .height(MaterialTheme.dimens.space1)
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.space16)
            )
        }

    }
}