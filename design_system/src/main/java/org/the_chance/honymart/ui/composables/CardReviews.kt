package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography

@Composable
fun CardReviews(
    userName: String,
    reviews: String,
    data: String,
    rating: Float,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = userName,
            style = Typography.displayLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        RatingBar(rating = rating)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = reviews,
                style = Typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = data,
                style = Typography.displaySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
        Divider(color = MaterialTheme.colorScheme.onTertiaryContainer)
    }
}

@Preview
@Composable
fun kkk() {
    CardReviews(userName = "hhh", reviews = "lkk", data = "kjhg", rating = 1.5f)
}