package com.example.coffick.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import java.time.Instant

data class CafeEntity(
    val id: Int,
    @SerialName("cafe_name") val cafeName: String,
    val tag: Int,
    val content: String,
    @SerialName("image_url") val image: String,
    val x: Double,
    val y: Double,
    val address: String,
    @SerialName("Editor'sPicks") val editorPick: Boolean,
    val isPublic: Boolean,
    @Contextual @SerialName("created_at") val createdAt: Instant?,
    @Contextual @SerialName("updated_at") val updatedAt: Instant?
)
