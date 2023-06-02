package data.remote.models

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val quantity: String,
    @SerializedName("price")
    val price: Double
)