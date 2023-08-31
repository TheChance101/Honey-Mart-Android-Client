package org.the_chance.honeymart.ui.features.category.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.category.CategoriesUiState
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductButton(
    state: CategoriesUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ContentVisibility(state = state.showScreenState.showFab) {
        Card(
            modifier = modifier.size(MaterialTheme.dimens.productButton),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(primary100),
            onClick = onClick
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add_new_category),
                    contentDescription = stringResource(R.string.icon),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(MaterialTheme.dimens.icon36)
                )
            }
        }
    }
}