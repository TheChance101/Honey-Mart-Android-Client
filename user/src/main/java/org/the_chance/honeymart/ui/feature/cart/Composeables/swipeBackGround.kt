package org.the_chance.honeymart.ui.feature.cart.Composeables

import androidx.compose.material3.Card
import androidx.compose.material3.DismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeDelete(state: DismissState) {
Card {
    Icon(painter = painterResource(id = org.the_chance.design_system.R.drawable.cart_delete),
        contentDescription = "DeleteIcon")
}
}