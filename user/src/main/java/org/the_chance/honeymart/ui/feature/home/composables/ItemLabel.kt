package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun ItemLabel(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconPainter: Painter = painterResource(id = R.drawable.ic_seall),
) {
    val currentLocale = LocalContext.current.resources.configuration.locales[0]
    val isRtl = currentLocale.language == "AR"

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (isRtl) {
            IconButton(
                modifier = Modifier.size(MaterialTheme.dimens.icon24),
                onClick = onClick,
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
            )
        } else {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(MaterialTheme.colorScheme.onSecondary),
            )
            IconButton(
                modifier = Modifier.size(MaterialTheme.dimens.icon24),
                onClick = onClick,
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
    }
}


@Preview
@Composable
fun ItemLabelPreview() {
    HoneyMartTheme {
        ItemLabel(label = "Label", onClick = { /*TODO*/ })
    }
}