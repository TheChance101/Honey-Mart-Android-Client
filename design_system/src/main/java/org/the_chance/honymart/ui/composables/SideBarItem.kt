package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun SideBarItem(
    modifier: Modifier = Modifier,
    icon: Int,
    categoryName: String,
    isSelected: Boolean,
    onClick: () -> Unit = {}
) {
    HoneyMartTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4)
        ) {
            IconButton(
                modifier = modifier
                    .size(MaterialTheme.dimens.space56)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary else White,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(bottom = MaterialTheme.dimens.space4),
                onClick = onClick
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "icon",
                    tint = if (isSelected) White else black37
                )
            }
            Text(
                text = categoryName,
                style = MaterialTheme.typography.displaySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = if (isSelected) MaterialTheme.colorScheme.primary else black37,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun SideBarItemPreview() {
    // SideBarItem()
}