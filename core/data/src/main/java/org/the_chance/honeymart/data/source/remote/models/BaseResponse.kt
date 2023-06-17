package org.the_chance.honeymart.data.source.remote.models

data class BaseResponse<T>(
    val value: T?,
    val isSuccess: Boolean,
    val status: StatusResponse
)
