package com.yj.coffick.core.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CafeTaggingEntity(
    @SerialName("cafe_id") val cafeId: Int,
    @SerialName("cafe_name") val cafeName: String?,
    val content: String?,
    val address: String?,
    val longitude: Double?,
    val latitude: Double?,
    @SerialName("EditorPick") val editorPick: Boolean,
    val isPublic: Boolean?,
    val tags: List<String?>
)