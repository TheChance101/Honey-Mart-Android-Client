package org.the_chance.honeymart.ui.addCategory.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.owner_black637
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white100

@Composable
fun CategoryImage(
    iconPainter: Painter,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    categoryImageID: Int,
    onClick: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier.width(82.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4)
    ) {
        IconButton(
            modifier = modifier
                .size(82.dp)
                .background(
                    if (isSelected) primary100
                    else white100,
                    shape = Shapes.medium
                )
                .padding(end = MaterialTheme.dimens.space8),
            onClick = { onClick(categoryImageID) }
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = "",
                tint = if (isSelected) Color.White else owner_black637
            )
        }
    }

}

//@Composable
//@Preview
//fun previewCategoryImage(){
//    CategoryImage(
//        iconPainter = painterResource(id = org.the_chance.design_system.R.drawable.icon_cart),
//        isSelected = true,
//        onClick = {}
//    )
//}