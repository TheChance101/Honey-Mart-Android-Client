package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun SpacerHorizontal4() {
    Spacer(modifier = Modifier.width(MaterialTheme.dimens.space4))
}

