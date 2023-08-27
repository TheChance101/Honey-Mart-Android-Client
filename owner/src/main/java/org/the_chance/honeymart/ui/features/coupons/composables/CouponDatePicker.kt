package org.the_chance.honeymart.ui.features.coupons.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponDatePicker() {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        modifier = Modifier,
        onDismissRequest = {

        },
        confirmButton = {
            Button(
                modifier = Modifier.padding(
                    end = MaterialTheme.dimens.space16,
                    bottom = MaterialTheme.dimens.space16,
                ),
                onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    selectedDateMillis?.let { }
                },
            ) {
                Text(
                    text = "Done",
                    style = MaterialTheme.typography.displayLarge,
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                modifier = Modifier.padding(
                    bottom = MaterialTheme.dimens.space16,
                ), onClick = {

                }
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.displayLarge.copy(MaterialTheme.colorScheme.onSecondary),
                )
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
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


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun CouponDatePickerPreview() {
    HoneyMartTheme {
        CouponDatePicker()
    }
}