package org.the_chance.honeymart.ui.feature.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.white100

/**
 * Created by Aziza Helmy on 7/27/2023.
 */
@Composable
fun CategoryItem(state: CategoryUiState, onCategoryClicked: (Long) -> Unit) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { onCategoryClicked }
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .background(white100)
                .size(120.dp), contentAlignment = Center
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = org.the_chance.design_system.R.drawable.icon_category),
                contentDescription = "category image",
                contentScale = ContentScale.FillWidth,
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewCategoryItem() {
    CategoryItem(CategoryUiState(categoryName = "Honey"), onCategoryClicked = {})
}