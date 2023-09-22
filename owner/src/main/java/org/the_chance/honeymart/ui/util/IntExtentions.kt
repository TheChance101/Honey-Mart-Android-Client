package org.the_chance.honeymart.ui.util

fun Int.defaultTo1IfZero(): Int {
    return if (this == 0) 1 else this
}