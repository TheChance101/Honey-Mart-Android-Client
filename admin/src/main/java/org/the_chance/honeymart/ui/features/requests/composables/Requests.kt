package org.the_chance.honeymart.ui.features.requests.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun Requests() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space20),
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
        ) {
            CustomChip(
                state = true,
                text = stringResource(R.string.all_requests),
                onClick = {}
            )
            CustomChip(
                state = false,
                text = stringResource(R.string.new_requests),
                onClick = {}
            )
            CustomChip(
                state = false,
                text = stringResource(R.string.approved),
                onClick = {}
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(4) {
                ItemRequest(
                    onClickCard = { },
                    userName = "Menna",
                    marketName = "HoneyMart",
                    date = "8 Aug 08:08 pm"
                )
            }
        }
    }
}