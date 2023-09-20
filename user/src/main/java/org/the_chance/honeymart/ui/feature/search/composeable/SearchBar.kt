package org.the_chance.honeymart.ui.feature.search.composeable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun SearchBar(
    query: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        singleLine = true,
        modifier = modifier
            .height(MaterialTheme.dimens.space48)
            .border(
                width = MaterialTheme.dimens.space1,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                shape = Shapes.medium
            ),
        value = query,
        onValueChange = onValueChange,
        shape = Shapes.medium,
        textStyle = MaterialTheme.typography.displaySmall,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.dimens.icon24),
                painter = painterResource(id = R.drawable.search),
                contentDescription = stringResource(R.string.copy_button),
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        },
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    HoneyMartTheme {
        SearchBar(
            query = "",
            onValueChange = {},
        )
    }
}