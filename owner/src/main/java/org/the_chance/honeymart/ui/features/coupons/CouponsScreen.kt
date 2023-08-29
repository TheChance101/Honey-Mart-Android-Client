package org.the_chance.honeymart.ui.features.coupons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.components.ContentVisibility
import org.the_chance.honeymart.ui.features.category.composable.HoneyMartTitle
import org.the_chance.honeymart.ui.features.coupons.content.AddCouponContent
import org.the_chance.honeymart.ui.features.coupons.content.ProductSearchContent
import org.the_chance.honymart.ui.composables.ConnectionErrorPlaceholder
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.design_system.R
import java.util.Calendar

@Composable
fun CouponsScreen(
    viewModel: CouponsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    CouponsContent(state = state, listener = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponsContent(
    state: CouponsUiState,
    listener: CouponsInteractionListener,
) {
    ConnectionErrorPlaceholder(state = state.showConnectionError()) {
        listener.getMarketProducts()
    }

    ContentVisibility(state = state.showContent()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                HoneyMartTitle()
                ProductSearchContent(
                    state = state.productSearch,
                    listener = listener,
                )
            }

            AddCouponContent(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = MaterialTheme.dimens.space56),
                state = state.addCoupon,
                listener = listener,
            )

            ContentVisibility(state = state.isDatePickerVisible) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = state.addCoupon.expirationDate?.time
                )
                DatePickerDialog(
                    onDismissRequest = listener::onDatePickerDismiss,
                    confirmButton = {
                        Button(
                            modifier = Modifier.padding(
                                end = MaterialTheme.dimens.space16,
                                bottom = MaterialTheme.dimens.space16,
                            ),
                            onClick = {
                                val selectedDateMillis = datePickerState.selectedDateMillis
                                selectedDateMillis?.let {
                                    listener.onDatePickerDoneClick(it)
                                }
                            },
                        ) {
                            Text(
                                text = stringResource(id = R.string.done),
                                style = MaterialTheme.typography.displayLarge,
                            )
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            modifier = Modifier.padding(
                                bottom = MaterialTheme.dimens.space16,
                            ), onClick = listener::onDatePickerDismiss
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                style = MaterialTheme.typography.displayLarge.copy(MaterialTheme.colorScheme.onSecondary),
                            )
                        }
                    },
                    tonalElevation = 0.dp,
                ) {
                    DatePicker(
                        state = datePickerState,
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimens.space16),
                        colors = DatePickerDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            headlineContentColor = MaterialTheme.colorScheme.onSecondary,
                            yearContentColor = MaterialTheme.colorScheme.onSecondary,
                            weekdayContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        ),
                        showModeToggle = false,
                        dateValidator = { date ->
                            date > Calendar.getInstance().timeInMillis
                        },
                        title = null,
                    )
                }
            }
        }
    }
}

@Preview(device = "id:pixel_tablet")
@Composable
fun CouponsScreenPreview() {
    HoneyMartTheme {
        CouponsScreen()
    }
}