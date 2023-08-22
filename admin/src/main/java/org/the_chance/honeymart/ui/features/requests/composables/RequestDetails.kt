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
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.features.requests.RequestsInteractionListener
import org.the_chance.honeymart.ui.features.requests.RequestsUiState
import org.the_chance.honymart.ui.composables.HoneyOutlineButton
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white
import java.util.Locale

@Composable
fun RequestDetails(
    state: RequestsUiState,
    listener: RequestsInteractionListener
) {
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
                if (state.ownerImage.isNotEmpty()) {
                    ImageNetwork(
                        modifier = Modifier
                            .size(MaterialTheme.dimens.icon48)
                            .clip(CircleShape),
                        imageUrl = state.ownerImage,
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(MaterialTheme.dimens.icon48)
                            .clip(CircleShape)
                            .background(
                                MaterialTheme.colorScheme.primary,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = state.ownerNameFirstCharacter.toString().uppercase(Locale.ROOT),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = white
                            )
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(top = MaterialTheme.dimens.space8),
                    horizontalAlignment = Start,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                ) {
                    Text(
                        text = state.ownerName,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = state.ownerEmail,
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
                text = state.marketName,
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
                    text = state.marketAddress,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.displayLarge
                )
            }
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space112),
                text = state.marketDescription,
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
                    onClick = listener::onClickCancel,
                    label = stringResource(R.string.cancel),
                )
                HoneyOutlineButton(
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.primary,
                        Shapes.medium
                    ),
                    onClick = listener::onClickApprove,
                    label = stringResource(R.string.approve),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}