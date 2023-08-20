package org.the_chance.honeymart.ui.features.requests.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Composable
fun ItemRequest(
    onClickCard: () -> Unit,
    userName: String,
    marketName: String,
    date: String,
    onCardSelected: Boolean,
    isRequestNew: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .clickable { onClickCard() },
        colors = CardDefaults.cardColors(containerColor = (white))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            ContentVisibility(state = onCardSelected) {
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(10.dp),
                    thickness = 100.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.dimens.space16)
                    .padding(vertical = MaterialTheme.dimens.space8),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
            ) {
                ImageNetwork(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.space86)
                        .clip(CircleShape),
                    imageUrl = "",
                )
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                ) {
                    Text(
                        text = userName,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = MaterialTheme.dimens.space16),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = marketName,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.displayLarge
                        )
                        ContentVisibility(state = isRequestNew) {
                            Box(
                                modifier = Modifier
                                    .size(MaterialTheme.dimens.space16)
                                    .clip(shape = CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
                        verticalAlignment = CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_clock),
                            contentDescription = "time",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        )
                        Text(
                            text = date,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                }
            }
        }
    }
}