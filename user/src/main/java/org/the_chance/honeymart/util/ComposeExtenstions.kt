package org.the_chance.honeymart.util

import android.icu.text.DecimalFormat

fun formatCurrencyWithNearestFraction(amount: Double):String {
    val decimalFormat = DecimalFormat("#,##0.0'$'")
    return decimalFormat.format(amount)
}