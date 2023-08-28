package org.the_chance.honeymart.ui.features.markets.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.features.markets.RequestsState
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white
import java.util.Locale

@Composable
fun ItemMarketRequest(
    onClickCard: () -> Unit,
    ownerName: String,
    marketName: String,
    state: Boolean,
    marketStateText: String,
    requestsState: RequestsState,
    ownerNameFirstCharacter: Char,
    onCardSelected: Boolean,
    isLoading: Boolean,
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
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            ContentVisibility(state = onCardSelected) {
                Divider(
                    modifier = Modifier.fillMaxHeight().width(10.dp),
                    thickness = 100.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = MaterialTheme.dimens.space16)
                    .padding(vertical = MaterialTheme.dimens.space16),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
            ) {
                Box(
                    modifier = Modifier.size(MaterialTheme.dimens.space56).clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary,),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = ownerNameFirstCharacter.toString().uppercase(Locale.ROOT),
                        style = MaterialTheme.typography.headlineMedium.copy(color = white)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
                ) {
                    Text(
                        text = ownerName,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            text = marketName,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        AnimatedVisibility(
                            visible = requestsState == RequestsState.ALL && !isLoading,
                            enter = slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(1000)),
                            exit = slideOutHorizontally(targetOffsetX = { it },animationSpec = tween(500)),
                        ){
                            Text(
                                text = marketStateText,
                                color = if (state) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSecondaryContainer,
                                style = MaterialTheme.typography.displaySmall,
                                modifier = Modifier.padding(end = 32.dp)
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.padding(MaterialTheme.dimens.space16))
            }
        }
    }
}

@Preview
@Composable
fun ItemRequestPreview() {
    ItemMarketRequest(
        {},
        "shehab",
        "market",
        true,
        "pending",
        RequestsState.APPROVED,
        'U',
        true,
        false
    )
}
