package org.the_chance.honeymart.ui.feature.product_details.composeable

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomSmallIconButton
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        navigationIcon = {
            CustomSmallIconButton(
                idIconDrawableRes = R.drawable.icon_arrow_back,
                onClick = onBackClick,
                background = MaterialTheme.colorScheme.background
            )
        },
        title = {
        },
        actions = {
            CustomSmallIconButton(
                idIconDrawableRes = R.drawable.icon_favorite_unselected,
                onClick = onFavoriteClick
            )
        },
    )
}


@Preview(showBackground = true)
@Composable
private fun AppBarPreview() {
    HoneyMartTheme {
        AppBar(onBackClick = {}, onFavoriteClick = {})
    }
}