package org.the_chance.honeymart.ui.feature.order_details.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.gowtham.ratingbar.RatingBar
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.feature.order_details.AddReviewBottomSheetUiState
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
    AnimatedVisibility(
        visible = state.isVisible,
        enter = slideInVertically { it },
        exit = slideOutVertically { -it },
    ) {
        ModalBottomSheet(
            modifier = Modifier.navigationBarsPadding(),
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
                text = "Add your rate",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary
            )

            RatingBar(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = MaterialTheme.dimens.space8),
                value = state.rating,
                painterEmpty = painterResource(id = R.drawable.ic_star),
                painterFilled = painterResource(id = R.drawable.ic_star_filled),
                onValueChange = { onRatingChange(it) },
                onRatingChanged = { },
            )

            Text(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.dimens.space16,
                        start = MaterialTheme.dimens.space16
                    ),
                text = "Add your review",
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
                value = state.review,
                onValueChange = { onReviewChange(it) },
                shape = Shapes.medium,
                textStyle = MaterialTheme.typography.displayLarge,
                maxLines = 10,
                minLines = 10,
                placeholder = {
                    Text(
                        text = "What did you like or dislike? How did you use the product? What should others know before buying?",
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.displayLarge,
                    )
                },
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
                label = "Submit Review",
                onClick = onClickSubmit,
                isLoading = state.isLoading,
                isButtonEnabled = state.isSubmitEnabled,
            )

            Spacer(modifier = Modifier.weight(1f))
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