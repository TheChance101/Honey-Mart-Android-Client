package org.the_chance.honeymart.ui.features.requests.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.HoneyOutlineButton
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Composable
fun RequestDetails() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(Shapes.medium)
            .background(white)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_honey_sun),
            contentDescription = "",
            modifier = Modifier.align(Alignment.TopEnd)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
        ) {
            Row(
                modifier = Modifier.align(Start),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Divider(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.space12, 100.dp)
                        .clip(RoundedCornerShape(bottomEnd = MaterialTheme.dimens.space16)),
                    thickness = 100.dp,
                    color = MaterialTheme.colorScheme.primary
                )
                ImageNetwork(
                    modifier = Modifier
                        .padding(top = MaterialTheme.dimens.space8)
                        .size(70.dp)
                        .clip(CircleShape),
                    imageUrl = "",
                )
                Column(
                    modifier = Modifier.padding(top = MaterialTheme.dimens.space8),
                    horizontalAlignment = Start,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                ) {
                    Text(
                        text = "Menna",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "menna@gmail.com",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            }
            Image(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.dimens.space32)
                    .size(327.dp, 184.dp)
                    .clip(Shapes.small)
                    .align(CenterHorizontally),
                painter = painterResource(R.drawable.fruite_image),
                contentDescription = "",
            )
            Text(
                text = "Contemporary Touli Market",
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.headlineMedium,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_map_point),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "Knowledge St. Modern Touli District",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.displayLarge
                )
            }
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space112),
                text = "A supermarket is a large retail store that offers a wide range of groceries, household items, and various consumer products under one roof.",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HoneyOutlineButton(
                    onClick = { },
                    label = stringResource(R.string.cancel),
                )
                HoneyOutlineButton(
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.primary,
                        Shapes.medium
                    ),
                    onClick = { },
                    label = stringResource(R.string.approve),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview
@Composable
fun RequestDetailsPreview() {
    RequestDetails()
}