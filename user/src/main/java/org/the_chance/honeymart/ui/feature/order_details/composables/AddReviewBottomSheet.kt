package org.the_chance.honeymart.ui.feature.order_details.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.order_details.AddReviewBottomSheetUiState
import org.the_chance.honeymart.ui.feature.order_details.isButtonEnabled
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewBottomSheet(
    state: AddReviewBottomSheetUiState,
    onDismiss: () -> Unit,
    onClickSubmit: () -> Unit,
    onRatingChange: (Float) -> Unit,
    onReviewChange: (String) -> Unit,
) {

    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = { it != SheetValue.Expanded },
        skipPartiallyExpanded = true
    )

    AnimatedVisibility(
        modifier = Modifier.navigationBarsPadding(),
        visible = state.isVisible,
        enter = slideInVertically { it },
        exit = slideOutVertically { -it },
    ) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismiss,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            dragHandle = {
                BottomSheetDefaults.DragHandle(
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            },
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.add_your_rate),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary
            )

            RatingBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimens.space8),
                rating = state.rating,
                onRatingChanged = { onRatingChange(it) },
                size = MaterialTheme.dimens.space32,
            )

            Text(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.dimens.space16,
                        start = MaterialTheme.dimens.space16
                    ),
                text = stringResource(R.string.add_your_review),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.dimens.space8,
                        start = MaterialTheme.dimens.space16,
                        end = MaterialTheme.dimens.space16,
                    ),
                value = state.reviewState.value,
                onValueChange = { onReviewChange(it) },
                shape = Shapes.medium,
                textStyle = MaterialTheme.typography.displayLarge,
                maxLines = 10,
                minLines = 10,
                placeholder = {
                    Text(
                        text = stringResource(R.string.what_did_you_like_or_dislike_how_did_you_use_the_product_what_should_others_know_before_buying),
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.displayLarge,
                    )
                },
                supportingText = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = state.reviewState.errorState,
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.displayLarge,
                        )

                        Text(
                            text = state.limit,
                            textAlign = TextAlign.End,
                            color = if (state.reviewState.isValid)
                                MaterialTheme.colorScheme.onTertiaryContainer
                            else MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.displayLarge,
                        )
                    }
                },
                isError = !state.reviewState.isValid,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedSupportingTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = MaterialTheme.colorScheme.onTertiary,
                    focusedBorderColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onTertiaryContainer,
                ),
            )

            HoneyFilledButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        vertical = MaterialTheme.dimens.space16,
                    )
                    .wrapContentSize(),
                label = stringResource(R.string.submit_review),
                onClick = onClickSubmit,
                isLoading = state.isLoading,
                isButtonEnabled = state.isButtonEnabled(),
            )
        }
    }
}

@Preview
@Composable
fun AddReviewBottomSheetPreview() {
    HoneyMartTheme {
        AddReviewBottomSheet(
            state = AddReviewBottomSheetUiState(isVisible = true),
            onDismiss = {},
            onClickSubmit = {},
            onRatingChange = {},
            onReviewChange = {},
        )
    }
}