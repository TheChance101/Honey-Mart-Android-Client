package org.the_chance.honeymart.ui.features.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.orders.composables.AllOrdersContent
import org.the_chance.honeymart.ui.features.orders.composables.EmptyOrdersPlaceholder

@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    OrdersContent(state, viewModel)
}

@Composable
fun OrdersContent(
    state: OrdersUiState,
    listener: OrdersInteractionsListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        HoneyMartTitle()
        EmptyOrdersPlaceholder(
            painter = painterResource(id = R.drawable.owner_empty_order),
            text = stringResource(R.string.there_are_no_order_for_this_day),
            visibilityState = state.emptyOrdersPlaceHolder(),

            )
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                ContentVisibility(state = state.screenContent())
                {
                    AllOrdersContent(state = state, listener = listener)
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {

            }

        }
    }
}