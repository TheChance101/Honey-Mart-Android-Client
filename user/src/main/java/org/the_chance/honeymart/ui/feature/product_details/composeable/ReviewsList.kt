package org.the_chance.honeymart.ui.feature.product_details.composeable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honeymart.ui.feature.product_details.ReviewUiState
import org.the_chance.honymart.ui.composables.RatingBar
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsList(
    modifier: Modifier = Modifier,
    reviews: List<ReviewUiState>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(
                top = MaterialTheme.dimens.space8,
                start = MaterialTheme.dimens.space8,
                end = MaterialTheme.dimens.space16
            )
    ) {
        items(reviews.size) { index ->
            val review = reviews[index]
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = review.fullName ?: "",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                RatingBar(rating = review.rating.toFloat())
                var textFieldContent by remember { mutableStateOf(review.content ?: "") }
                TextField(
                    value = textFieldContent,
                    onValueChange = { textFieldContent = it },
                    label = { Text("Review Content") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                        focusedLabelColor = MaterialTheme.colorScheme.secondary,
                        cursorColor = MaterialTheme.colorScheme.background
                    )
                )
                Text(
                    text = review.reviewDate.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.background
                )
            }
            if (index != reviews.size - 1) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

//@Preview(showBackground = true, name = "Reviews List Preview")
//@Composable
//fun ReviewsListPreview() {
//    val sampleReviews = listOf(
//        ReviewUiState(
//            reviewId = 1L,
//            content = "This is a sample review",
//            rating = 5,
//            reviewDate = 1L,
//            fullName = "John Doe"
//        ),
//        ReviewUiState(
//            reviewId = 2L,
//            content = "Another review example",
//            rating = 4,
//            reviewDate = 2L,
//            fullName = "Jane Smith"
//        )
//    )
//
//    ReviewsList(reviews = sampleReviews)
//}
