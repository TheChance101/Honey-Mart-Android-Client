package org.the_chance.honymart.ui.composables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun SnackBar(
    message: String,
    show: Boolean,
    onHideSnackbar: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val snackBar = createRef()
        if (show) {
            Snackbar(
                modifier = Modifier
                    .constrainAs(snackBar) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = MaterialTheme.dimens.space16),
                action = {
                    Text(
                        "Undo",
                        modifier = Modifier.clickable(onClick = onHideSnackbar),
                        style = Typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
            ) {
                Text(
                    text = message,
                    style = Typography.bodySmall,
                )
            }
        }
    }
}