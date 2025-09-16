package com.example.coffick.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedMenuEntity(
    val id: Int,
    @SerialName("cafe_id") val cafeId: Int,
    val menu: String
)
