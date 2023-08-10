package org.the_chance.honymart.ui.composables
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.dimens

/**
 * Created by Aziza Helmy on 8/9/2023.
 */

@Composable
fun HoneyAppBarTitle(
    titleColor: Color,
    modifier: Modifier=Modifier
) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .size(MaterialTheme.dimens.icon32)
                .padding(end = MaterialTheme.dimens.space4),
            painter = painterResource(id = R.drawable.icon_cart),
            contentDescription = stringResource(R.string.title_icon),
            tint = titleColor
        )
        Text(
            style = MaterialTheme.typography.displayMedium.copy(color = titleColor),
            text = stringResource(R.string.honey)
        )
        Text(
            text = stringResource(R.string.mart),
            style = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSecondary),

            )
    }
}