import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun SwipeBackground() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.space2)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            ),
    ) {
        Image(
            painter = painterResource(id = R.drawable.cart_delete),
            contentDescription = "DeleteIcon",
            modifier = Modifier.align(Alignment.CenterEnd),
        )
    }
}

@Preview
@Composable
fun SwipeBackgroundPreview() {
    HoneyMartTheme {
        SwipeBackground()
    }
}