package org.the_chance.honeymart.data.source.remote.util

import java.util.Date

internal fun Long.convertTimestampToDate(): Date {
    return Date(this)
}