package org.the_chance.honeymart.ui.feature.category.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun CategoriesScreenTopBar(
    onClickBack: () -> Unit,
    titleColor: Color = MaterialTheme.colorScheme.onSecondary,
    iconColor: Color = MaterialTheme.colorScheme.onSecondary,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Card(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space12))
                    .clickable(onClick = onClickBack),
                colors = CardDefaults.cardColors(colorResource(id = R.color.white))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_arrow_back),
                    contentDescription = stringResource(id = R.string.back_button),
                    tint = iconColor,
                    modifier = Modifier.padding(MaterialTheme.dimens.space12)
                )
            }
        }
        Text(
            text = stringResource(id = R.string.shop_details),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = titleColor
        )
        Spacer(modifier = Modifier
            .width(0.dp)
            .weight(1f))
    }
}