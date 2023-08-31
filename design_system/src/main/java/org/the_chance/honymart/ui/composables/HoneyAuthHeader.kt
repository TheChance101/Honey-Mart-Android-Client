package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.black87
import org.the_chance.honymart.ui.theme.dimens

/**
 * Created by Aziza Helmy on 8/9/2023.
 */
@Composable
fun HoneyAuthHeader(
    title: String,
    subTitle: String,
    modifier: Modifier = Modifier,
    titleColor: Color = MaterialTheme.colorScheme.onSecondary,
    subTitleColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space24)
    ) {
        Text(
            text = title,
            style = Typography.headlineMedium.copy(color = titleColor),
            textAlign = TextAlign.Center,
        )
        Text(
            text = subTitle,
            style = Typography.bodyMedium.copy(color = subTitleColor),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun PreviewHoneyAuthHeader(){
    HoneyAuthHeader(
        title = stringResource(R.string.sign_up),
        subTitle = stringResource(R.string.create_an_account_name_your_market),
    )
}
