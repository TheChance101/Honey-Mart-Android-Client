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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun Placeholder(
    painter: Painter,
    text: String,
) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimens.space16),
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
                    .copy(MaterialTheme.colorScheme.onBackground),
            )
        }
    }
