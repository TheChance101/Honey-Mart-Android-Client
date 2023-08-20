package org.the_chance.honeymart.ui.features.category.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItem(
    icon: Int,
    categoryName: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space8)
    ) {
        Card(
            modifier = Modifier.size(140.dp),
            shape = MaterialTheme.shapes.medium,

            colors = if (isSelected) CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
            else CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
            onClick = onClick
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = MaterialTheme.dimens.space4),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(R.string.icon),
                    tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(MaterialTheme.dimens.icon48)
                )
            }
        }
        Text(
            text = categoryName,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

    }


}