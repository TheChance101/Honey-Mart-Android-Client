package org.the_chance.honeymart.ui.features.category.composable

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
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
    AnimatedVisibility(visible = state.showScreenState.showFab) {
        Card(
            modifier = modifier.size(96.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(primary100),
            onClick = onClick
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_cart),
                    contentDescription = stringResource(R.string.icon),
                    tint = Color.White,
                    modifier = Modifier.size(MaterialTheme.dimens.icon36)
                )
            }
        }
    }
}

@Preview
@Composable
private fun addProductPreview() {
//    AddProductButton()
}