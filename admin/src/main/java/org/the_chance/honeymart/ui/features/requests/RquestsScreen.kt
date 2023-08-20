package org.the_chance.honeymart.ui.features.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.HoneyMartTitle
import org.the_chance.honeymart.ui.features.requests.composables.ItemRequest
import org.the_chance.honymart.ui.composables.CustomChip

@Composable
fun RequestsScreen() {
    RequestsContent()
}

@Composable
fun RequestsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        HoneyMartTitle()
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f).padding(start = 40.dp)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                    modifier = Modifier.fillMaxHeight().padding(top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    items(4){
                        ItemRequest(
                            onClickCard = {  },
                            userName = "Menna",
                            marketName = "HoneyMart",
                            date = "8 Aug 08:08 pm"
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ){}
        }
    }
}