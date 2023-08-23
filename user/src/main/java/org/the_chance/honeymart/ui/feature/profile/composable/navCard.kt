package org.the_chance.honeymart.ui.feature.profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun NavCard(
    title: String,
    iconId: Int,
    color: Color = MaterialTheme.colorScheme.onBackground,
    onClick : () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onTertiary,
                shape = MaterialTheme.shapes.small,
            )
            .clip(shape = MaterialTheme.shapes.small)
            .clickable {onClick()}
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space12
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "",
                tint = color,
                modifier = Modifier.padding(end = 4.dp),

                )
            Text(
                text = title,
                style = MaterialTheme.typography.displayLarge,
                color = color,
            )
        }
    }
}