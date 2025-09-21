package com.yj.coffick.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CafeImages(
    val id: Int,
    @SerialName("img_url") val imgUrl: String?,
    @SerialName("cafe_id") val cafeId: Int?,
)
