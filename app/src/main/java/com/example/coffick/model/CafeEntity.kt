package com.example.coffick.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class CafeEntity(
    val id: Int,
    @SerialName("cafe_name") val cafeName: String?,
    val tag: String?,
    val content: String?,
    @SerialName("image_url") val image: String?,
    val longitude: Double?,
    val latitude: Double?,
    val address: String?,
    @SerialName("Editor's Picks") val editorPick: Boolean,
    val isPublic: Boolean?,
    @SerialName("created_at") val createdAt: String?,
    @SerialName("updated_at") val updatedAt: String?,
    @SerialName("deleted_at") val deletedAt: String?
)
