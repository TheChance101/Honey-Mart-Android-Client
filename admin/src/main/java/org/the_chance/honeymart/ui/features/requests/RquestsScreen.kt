package org.the_chance.honeymart.ui.features.requests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.the_chance.honeymart.ui.composables.HoneyMartTitle
import org.the_chance.honeymart.ui.features.requests.composables.RequestDetails
import org.the_chance.honeymart.ui.features.requests.composables.Requests
import org.the_chance.honymart.ui.theme.dimens

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
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.dimens.space40),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Requests()
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                RequestDetails()
            }
        }
    }
}