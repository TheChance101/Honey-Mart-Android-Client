package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.error
import org.the_chance.honymart.ui.theme.white200

@Composable
fun HoneyTextFieldPassword(
    hint: String,
    iconPainter: Painter,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    text: String = "",
    errorMessage: String = "",
    isError: Boolean = errorMessage.isNotEmpty(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Search
    )
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val icon =
        if (passwordVisible)
            painterResource(id = R.drawable.eye) else
            painterResource(id = R.drawable.eye_closed)
    Column {
        OutlinedTextField(

            modifier = modifier
                .fillMaxWidth()
                .padding(
                    end = MaterialTheme.dimens.space16,
                    start = MaterialTheme.dimens.space16,
                    bottom = MaterialTheme.dimens.space8
                )
                .height(MaterialTheme.dimens.heightOutlinedTextField),
            keyboardActions = KeyboardActions(),
            singleLine = true,
            value = text,
            onValueChange = onValueChange,
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            label = {
                Text(
                    text = hint,
                    color = if (isError) error else black37,
                    style = Typography.displaySmall,
                )
            },
            keyboardOptions = keyboardOptions,
            shape = Shapes.medium,
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedSupportingTextColor = if (isError) error else MaterialTheme.colorScheme.onTertiaryContainer,
                focusedContainerColor = (Color.Transparent),
                disabledContainerColor = (MaterialTheme.colorScheme.onTertiary),
                focusedBorderColor = if (isError) error else MaterialTheme.colorScheme.onTertiaryContainer,
                unfocusedBorderColor = if (isError) error else MaterialTheme.colorScheme.onTertiaryContainer,
            ),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    backgroundColor = Color.Transparent
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = stringResource(R.string.icon_visible),
                        tint = if (isError) error else white200
                    )
                }
                if (isError) {
                    Icon(
                        painter = icon,
                        contentDescription = stringResource(R.string.copy_button),
                        tint = error
                    )
                }
            },
            leadingIcon = {
                Icon(
                    painter = iconPainter,
                    contentDescription = stringResource(R.string.copy_button),
                    tint = if (isError) error else white200
                )
            },
            isError = isError,
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = error,
                style = Typography.displaySmall,
                modifier = Modifier.padding(
                    start = MaterialTheme.dimens.space16,
                    top = MaterialTheme.dimens.zero
                )
            )
        }
    }
}





