package org.the_chance.honeymart.ui.category

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CategoryScreen() {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Category",
        textAlign = TextAlign.Center
    )
}
