package org.the_chance.honeymart.ui.features.orders.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.blackOn37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun OrderPlaceHolder(
    painter: Painter,
    text: String,
    onClick: () -> Unit,
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
        Row(
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.space8)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                modifier = Modifier
                    .padding(end = MaterialTheme.dimens.space8)
                    .align(Alignment.CenterVertically),
                text = stringResource(R.string.please_click_on),
                style = Typography.displayLarge.copy(color = blackOn37),
            )
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(primary100),
                content = {
                    Icon(
                        modifier = Modifier.size(MaterialTheme.dimens.icon14),
                        painter = painterResource(id = R.drawable.icon_refresh),
                        contentDescription = "",
                        tint = Color.White
                    )
                },
                onClick = onClick
            )
            Text(
                modifier = Modifier
                    .padding(start = MaterialTheme.dimens.space8)
                    .align(Alignment.CenterVertically),
                text = stringResource(R.string.to_refresh_orders),
                style = Typography.displayLarge.copy(color = blackOn37)
            )
        }
    }
}


@Preview
@Composable
fun PreviewOrderPlaceHolder() {

}