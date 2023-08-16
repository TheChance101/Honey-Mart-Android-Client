package org.the_chance.honeymart.ui.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomChip
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.IconButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun SearchScreen() {

}


@Preview(showSystemUi = true)
@Composable
fun SearchContent() {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)) {
            HoneyTextField(
                hint = "Search",
                iconPainter = painterResource(id = R.drawable.search),
                onValueChange = {},
                color = black37
            )
            IconButton(
                size = MaterialTheme.dimens.icon48,
                onClick = {},
                backgroundColor = primary100
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "",
                    tint = Color.White,
                )
            }
        }
        Text(
            text = "Sort by price",
            color = black37,
            modifier = Modifier.padding(
                start = MaterialTheme.dimens.space16,
                bottom = MaterialTheme.dimens.space8,
                top = MaterialTheme.dimens.space16
            ),
            style = Typography.displaySmall
        )
        Row(
            modifier = Modifier.padding(start = MaterialTheme.dimens.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
        ) {
            CustomChip(state = true, text = "Random", onClick = {})
            CustomChip(state = false, text = "Ascending", onClick = {})
            CustomChip(state = false, text = "Descending", onClick = {})
        }


    }


}