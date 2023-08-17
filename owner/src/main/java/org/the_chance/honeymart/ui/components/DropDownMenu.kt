package org.the_chance.honeymart.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.black8

@Composable
fun DropDownMenuList(
    onClickUpdate: () -> Unit,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer, shape = MaterialTheme.shapes.medium
                ).align(Alignment.Center)
                ,
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "update",
                        color = MaterialTheme.colorScheme.outlineVariant,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                },
                onClick = onClickUpdate
            )
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "delete",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                },
                onClick = onClickDelete
            )
        }
    }
}

@Preview
@Composable
fun PreviewDropDownMenu() {
    DropDownMenuList(onClickUpdate = { /*TODO*/ }, onClickDelete = { /*TODO*/ })

}
    
