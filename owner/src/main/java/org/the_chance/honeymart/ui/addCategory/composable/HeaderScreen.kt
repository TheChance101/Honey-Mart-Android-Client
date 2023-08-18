package org.the_chance.honeymart.ui.addCategory.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
@Composable
fun HeaderText(title: String){
    Row(
        modifier = Modifier.padding(start = 16.dp, top = 24.dp),
    ) {
        Icon(
            modifier = Modifier
                .width(24.dp)
                .height(24.dp),
            painter = painterResource(id = R.drawable.icon_add_new_category),
            contentDescription = ""
        )

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = title,
            style = Typography.displayMedium.copy(color = black60),
            textAlign = TextAlign.Center
        )
    }
}