
package org.the_chance.honeymart.ui.feature.wishlist.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.design_system.R
@Composable
fun NoConnectionError() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center ,
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.no_connection_placeholder) ,
            contentDescription =" " )
        Text( text = "Oops, No connection !!!",
            style = Typography.bodyMedium.copy(color = black60),
            textAlign = TextAlign.Center
        )
    }
}
@Preview
@Composable
fun PreviewNoConnectionError(){
    NoConnectionError()
}