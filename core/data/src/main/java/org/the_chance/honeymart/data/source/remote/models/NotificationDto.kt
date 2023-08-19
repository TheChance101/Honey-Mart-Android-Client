package org.the_chance.honeymart.data.source.remote.models

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("date")
    val date: String = ""
)
