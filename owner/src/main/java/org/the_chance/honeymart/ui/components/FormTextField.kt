package org.the_chance.honeymart.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.background
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun FormTextField(
    hint: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    text: String = "",
    errorMessage: String = "",
    isError: Boolean = errorMessage.isNotEmpty(),
    isEnable: Boolean = true
) {

    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.space16)
                .height(MaterialTheme.dimens.heightOutlinedTextField),
            value = text,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = hint,
                    color = if (isError) org.the_chance.honymart.ui.theme.error else black37,
                    style = Typography.displaySmall,
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = Shapes.medium,
            maxLines = 1,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedBorderColor = if (isError) org.the_chance.honymart.ui.theme.error else primary100,
                unfocusedBorderColor = if (isError) org.the_chance.honymart.ui.theme.error else black16,
            ),
            trailingIcon = {
                if (isError) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_error_password),
                        contentDescription = stringResource(R.string.copy_button),
                        tint = org.the_chance.honymart.ui.theme.error
                    )
                }
            },
            isError = isError,
            enabled = isEnable
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = org.the_chance.honymart.ui.theme.error,
                style = Typography.displaySmall,
                modifier = Modifier.padding(
                    start = MaterialTheme.dimens.space16,
                    top = MaterialTheme.dimens.zero
                )
            )
        }
    }
}

@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
private fun FormTextFieldPreview() {
    FormTextField(
        hint = "Email",
        isError = false,
        keyboardType = KeyboardType.Number,
        onValueChange = {}
    )
}