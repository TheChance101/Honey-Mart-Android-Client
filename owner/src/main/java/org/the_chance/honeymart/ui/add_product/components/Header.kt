package org.the_chance.honeymart.ui.add_product.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun Header(
    title: String,
    iconPainter: Painter,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.padding(
            horizontal = MaterialTheme.dimens.space16,
            vertical = MaterialTheme.dimens.space24
        ),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = "Icon Cart",
            modifier = Modifier
                .padding(end = MaterialTheme.dimens.space8)
                .size(MaterialTheme.dimens.icon24),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = title,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}