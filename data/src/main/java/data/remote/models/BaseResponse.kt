package data.remote.models

data class BaseResponse<T>(
    val value: T,
    val message: String?,
    val isSuccess: Boolean,
)
