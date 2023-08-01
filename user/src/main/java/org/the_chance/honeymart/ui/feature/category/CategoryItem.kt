package org.the_chance.honeymart.ui.feature.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60

/**
 * Created by Aziza Helmy on 7/27/2023.
 */
@Composable
fun CategoryItem(
    state: CategoryUiState,
    categoryId: Long,
    onCategoryClicked: (categoryId: Long, marketId: Long, position: Int) -> Unit,
    marketId: Long,
    position: Int,
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable { onCategoryClicked(categoryId, marketId, position) }
                .size(120.dp), contentAlignment = Center
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = org.the_chance.design_system.R.drawable.icon_category),
                contentDescription = "category image",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
        }
        Text(
            text = state.categoryName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = Typography.displayLarge,
            color = black60,
            textAlign = TextAlign.Center
        )
    }

}

