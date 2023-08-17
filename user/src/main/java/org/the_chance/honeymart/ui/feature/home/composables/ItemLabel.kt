package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black87

@Composable
fun ItemLabel(
    label: String,
    modifier: Modifier = Modifier,
    iconPainter: Painter = painterResource(id = R.drawable.seall),
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
    ) {
        Text(
            text = label,
            style = Typography.bodySmall.copy(black87),
        )
        Icon(
            painter = iconPainter,
            contentDescription = null,
            tint = black37,
        )
    }
}

@Preview
@Composable
fun ItemLabelPreview() {
    ItemLabel(label = "Label")
}