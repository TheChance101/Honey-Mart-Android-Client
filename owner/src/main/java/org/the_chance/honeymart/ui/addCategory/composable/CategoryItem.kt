package org.the_chance.honeymart.ui.addCategory.composable

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.the_chance.honeymart.ui.addCategory.HoneyMartTitle
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Composable
fun CategoryItem(
    categoryName: String,
    onCategoryClicked: (categoryId: Long, position: Int) -> Unit,
    position: Int,
    image: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = MaterialTheme.dimens.space8)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(shape = Shapes.medium)
                .background(white)
                .clickable { }
                .size(120.dp), contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.space48),
                painter = painterResource(id = image),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Text(
            text = categoryName,
            modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.dimens.space8),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = Typography.displayLarge.copy(black60),
            textAlign = TextAlign.Center
        )
    }

}