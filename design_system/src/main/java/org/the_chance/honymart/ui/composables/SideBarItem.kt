package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SideBarItem(
    modifier: Modifier = Modifier,
    backgroundButton: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {},
    icon: Int = 0,
    iconColor: Color = Color.White
) {
    IconButton(
        modifier = modifier.background(backgroundButton, shape = MaterialTheme.shapes.medium),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "icon",
            tint = iconColor
        )
    }
}

@Preview
@Composable
fun SideBarItemPreview() {
    SideBarItem()
}