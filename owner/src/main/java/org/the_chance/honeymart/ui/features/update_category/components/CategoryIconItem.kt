package org.the_chance.honeymart.ui.features.update_category.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryIconItem(
    iconPainter: Painter,
    isSelected: Boolean,
    categoryIconId: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.size(MaterialTheme.dimens.categoryIconItem),
        colors = if (isSelected) CardDefaults.cardColors(primary100)
        else CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
        onClick = { onClick(categoryIconId) },
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .size(MaterialTheme.dimens.icon48),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = stringResource(R.string.category_icon),
                modifier = Modifier.size(MaterialTheme.dimens.icon32),
                tint = if (isSelected) white
                else MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
@Preview
private fun PreviewCategoryIconItem() {
    HoneyMartTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)) {
            CategoryIconItem(
                iconPainter = painterResource(id = R.drawable.icon_cart),
                isSelected = true,
                categoryIconId = 3,
                onClick = { }
            )
            CategoryIconItem(
                iconPainter = painterResource(id = R.drawable.icon_category),
                isSelected = false,
                categoryIconId = 3,
                onClick = { }
            )
        }
    }
}