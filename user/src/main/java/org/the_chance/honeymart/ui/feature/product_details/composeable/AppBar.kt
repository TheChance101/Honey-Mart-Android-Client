package org.the_chance.honeymart.ui.feature.product_details.composeable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomSmallIconButton
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(

    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
        ),
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_back),
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(MaterialTheme.dimens.smallButton)
                    .background(MaterialTheme.colorScheme.background),
                tint = MaterialTheme.colorScheme.primary
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
//    Row(
//
//    ) {
//
//        CustomSmallIconButton(
//            idIconDrawableRes = R.drawable.icon_arrow_back,
//            onClick = onBackClick
//        )
//
//        CustomSmallIconButton(
//            idIconDrawableRes = R.drawable.icon_arrow_back,
//            onClick = onFavoriteClick
//        )
//
//    }
}


@Preview(showBackground = true)
@Composable
private fun AppBarPreview() {
    HoneyMartTheme {
        AppBar(onBackClick = {}, onFavoriteClick = {})
    }
}