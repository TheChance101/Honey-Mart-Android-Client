package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(
    message: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    openDialogCustom: MutableState<Boolean>,
) {
    AlertDialog(
        onDismissRequest = { openDialogCustom.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = true),
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_warning),
                    contentDescription = "Custom Icon",
                    modifier = Modifier
                        .padding(bottom = 32.dp)
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
                            .height(48.dp).padding(end = 8.dp),
                        onClick = onConfirm,
                        colors = ButtonDefaults.textButtonColors(primary100),
                        shape = Shapes.medium,
                        border = BorderStroke(width = 1.dp, color = black16)
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
                        colors = ButtonDefaults.textButtonColors(white),
                        shape = Shapes.medium,
                        border = BorderStroke(width = 1.dp, color = black16)
                    )
                    {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            style = Typography.displayLarge.copy(color = black37)
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewCustomAlertDialog() {
    CustomAlertDialog(
        message = "This is the dialog message.",
        onConfirm = {},
        onCancel = {},
        openDialogCustom = remember { mutableStateOf(false) },
    )
}
