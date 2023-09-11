package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun SearchBar(
    icon: Painter,
    onClick: () -> Unit,
    text : String = stringResource(R.string.search),
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .semantics {
                       contentDescription = "SearchWidget"
            },
        color = MaterialTheme.colorScheme.tertiaryContainer,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            MaterialTheme.dimens.space1,
            MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.dimens.space12,
                    horizontal = MaterialTheme.dimens.space16
                )
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(MaterialTheme.dimens.icon24)
                    .semantics {
                        contentDescription = "SearchIcon"
                    },
                tint = MaterialTheme.colorScheme.onBackground

            )
            Text(
                modifier = Modifier.semantics {
                contentDescription = "TextFiled"
                },
                text = text,
                style = Typography.displaySmall.copy(MaterialTheme.colorScheme.onSecondaryContainer),
            )
        }
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    HoneyMartTheme {
        SearchBar(
            icon = painterResource(id = R.drawable.ic_search),
            onClick = {}
        )
    }
}
