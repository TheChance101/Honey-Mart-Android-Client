package org.the_chance.honeymart.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun EmptyPlaceholder(
    state: Boolean,
    emptyObjectName: String,
    notificationState: Boolean =false ,
    notificationText : String =stringResource(R.string.recieve_notification)) {
    ContentVisibility(state = state) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.placeholder_wish_list),
                contentDescription = stringResource(R.string.empty_product),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimens.space32),
                text = stringResource(id = R.string.empty_wishlist_message, emptyObjectName),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimens.space16),
                text = if (notificationState) notificationText
                else stringResource(id = R.string.empty_wishlist_notification, emptyObjectName),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}