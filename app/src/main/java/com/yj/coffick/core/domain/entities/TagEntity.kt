package com.yj.coffick.core.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class TagEntity(
    val tag: String,
    val id: Int
)
