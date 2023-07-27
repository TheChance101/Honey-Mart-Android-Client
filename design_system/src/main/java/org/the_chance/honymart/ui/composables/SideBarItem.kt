package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
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
            modifier = modifier.width(MaterialTheme.dimens.space56),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space4)
        ) {
            HexagonItem(
                icon = icon,
                hexagonSize = MaterialTheme.dimens.space56,
                isSelected = isSelected,
                onClick = onClick
            )
            Text(
                text = categoryName,
                style = MaterialTheme.typography.displaySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color =
                    if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview
@Composable
fun SideBarItemPreview() {
    SideBarItem(categoryName = "Home", icon = R.drawable.ic_bed, isSelected = true)
}