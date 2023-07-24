package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun SpacerVertical8() {
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.space8))
}

@Composable
fun SpacerVertical16() {
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))
}

@Composable
fun SpacerVertical32() {
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.space32))
}
