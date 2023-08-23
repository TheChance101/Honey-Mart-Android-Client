package org.the_chance.honeymart.ui.feature.category.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.the_chance.honeymart.ui.feature.category.CategoryUiState
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.design_system.R

@Composable
fun CategoryItem(
    state: CategoryUiState,
    onCategoryClicked: (categoryId: Long, position: Int) -> Unit,
    position: Int
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = MaterialTheme.dimens.space8)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(shape = Shapes.medium)
                .background(color = colorResource(id = R.color.white))
                .clickable { onCategoryClicked(state.categoryId, position) }
                .size(120.dp), contentAlignment = Center
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = org.the_chance.design_system.R.drawable.icon_category),
                contentDescription = stringResource(R.string.category_image),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Text(
            text = state.categoryName,
            modifier = Modifier
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = Typography.displayLarge,
            color = black60,
            textAlign = TextAlign.Center
        )
    }

}

