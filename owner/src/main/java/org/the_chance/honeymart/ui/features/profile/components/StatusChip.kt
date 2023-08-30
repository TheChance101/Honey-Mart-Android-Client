package org.the_chance.honeymart.ui.features.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.success
import org.the_chance.honymart.ui.theme.error

@Composable
fun StatusChip(
    status: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = if (status) painterResource(id = R.drawable.icon_online_market)
            else painterResource(id = R.drawable.icon_offline_market),
            contentDescription = stringResource(R.string.icon_market_status),
            tint = if (status) success else error
        )
        Text(
            text = if (status) stringResource(R.string.online)
            else stringResource(R.string.offline),
            style = MaterialTheme.typography.displayLarge,
            color = if (status) success else error
        )
    }
}

@Preview
@Composable
fun PreviewStatusChip() {
    HoneyMartTheme {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatusChip(status = true)
            StatusChip(status = false)
        }
    }
}