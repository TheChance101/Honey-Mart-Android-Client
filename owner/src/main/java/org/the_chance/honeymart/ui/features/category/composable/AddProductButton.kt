package org.the_chance.honeymart.ui.features.category.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun AddProductButton(
    state: CategoriesUiState,
    onClick: () -> Unit,
) {
    ContentVisibility(state = state.showScreenState.showFab) {
        FloatingActionButton(
            shape = MaterialTheme.shapes.medium,
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_add_new_category),
                contentDescription = stringResource(R.string.icon),
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(MaterialTheme.dimens.icon24)
            )
        }

    }
}