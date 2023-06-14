package org.the_chance.honeymart.util

import java.util.regex.Pattern

/**
 * Created by Aziza Helmy on 6/15/2023.
 */


fun String.isEmail(): Boolean {
    return this.isNotEmpty() && Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        .matcher(this.fromArabic()).matches()
}

fun String.fromArabic(): String {
    var phoneOrName = this
    phoneOrName = phoneOrName.replace("٠", "0")
    phoneOrName = phoneOrName.replace("١", "1")
    phoneOrName = phoneOrName.replace("٢", "2")
    phoneOrName = phoneOrName.replace("٣", "3")
    phoneOrName = phoneOrName.replace("٤", "4")
    phoneOrName = phoneOrName.replace("٥", "5")
    phoneOrName = phoneOrName.replace("٦", "6")
    phoneOrName = phoneOrName.replace("٧", "7")
    phoneOrName = phoneOrName.replace("٨", "8")
    phoneOrName = phoneOrName.replace("٩", "9")
    return phoneOrName
}