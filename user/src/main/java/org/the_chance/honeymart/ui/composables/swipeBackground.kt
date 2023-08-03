import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R

@Composable
fun SwipeBackground() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp, end = 8.dp)) {
        Image(
            painter =
            painterResource(id = R.drawable.cart_delete),
            contentDescription = "DeleteIcon",
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}