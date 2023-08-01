package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun CustomAlertDialog(
    message: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = Shapes.extraLarge,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onTertiary)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_warning),
                    contentDescription = "warning Icon",
                    modifier = Modifier
                        .padding(bottom = 32.dp),
                )
                Text(
                    text = message,
                    color = black60,
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Center
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                ) {
                    TextButton(
                        modifier = Modifier
                            .width(144.dp)
                            .height(48.dp)
                            .padding(end = 8.dp),
                        onClick = onConfirm,
                        colors = ButtonDefaults.textButtonColors(primary100),
                        shape = Shapes.medium,
                    ) {
                        Text(
                            text = stringResource(id = R.string.yes_i_m_sure),
                            style = Typography.displayLarge.copy(color = white)
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .width(144.dp)
                            .height(48.dp),
                        onClick = onCancel,
                        colors = ButtonDefaults.textButtonColors(Color.Transparent),
                        shape = Shapes.medium,
                        border = BorderStroke(width = 1.dp, color = black16)
                    )
                    {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = Typography.displayLarge.copy(color = MaterialTheme.colorScheme.onTertiaryContainer)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomAlertDialog() {

}
