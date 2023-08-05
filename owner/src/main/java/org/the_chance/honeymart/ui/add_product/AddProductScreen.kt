package org.the_chance.honeymart.ui.add_product

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddProductScreen() {
    AddProductContent()
}

@Composable
fun AddProductContent() {

}

@Preview
@Preview(name = "Add Product Screen" , device = Devices.TABLET, showSystemUi = true)
@Composable
fun PreviewAddProductScreen() {
    AddProductScreen()
}