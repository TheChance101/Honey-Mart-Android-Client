package org.the_chance.honeymart.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun HoneyMartTitle(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(
            top = MaterialTheme.dimens.space56,
            start = MaterialTheme.dimens.space16,
            bottom = MaterialTheme.dimens.space24
        )
    ) {
        Icon(
            modifier = Modifier
                .size(MaterialTheme.dimens.icon32)
                .padding(end = MaterialTheme.dimens.space2),
            painter = painterResource(id = R.drawable.icon_cart),
            contentDescription = "",
            tint = primary100
        )
        Text(
            text = stringResource(id = R.string.honey),
            style = Typography.displayMedium.copy(color = primary100),
        )
        Text(
            text = stringResource(id = R.string.mart),
            style = Typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSecondary),
        )
    }
}