package org.the_chance.honeymart.ui.feature.marketInfo.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CardChip(
    text: String,
    icon: Painter? = null,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(MaterialTheme.dimens.space100),
        border = BorderStroke(
            MaterialTheme.dimens.space1,
            MaterialTheme.colorScheme.outline
        ),
        colors = CardDefaults.cardColors(colorResource(id = com.google.android.material.R.color.mtrl_btn_transparent_bg_color)),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = MaterialTheme.dimens.space8,
                horizontal = MaterialTheme.dimens.space16
            ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = stringResource(R.string.chip_icon),
                    tint = iconTint,
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}