package org.the_chance.honeymart.ui.orders

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun OrdersScreen() {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Orders",
        textAlign = TextAlign.Center
    )
}
