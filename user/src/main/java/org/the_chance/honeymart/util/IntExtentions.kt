package org.the_chance.honeymart.util

fun Int.defaultTo1IfZero(): Int {
    return if (this == 0) 1 else this
}