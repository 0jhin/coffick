package com.yj.coffick.model

import kotlinx.serialization.Serializable

@Serializable
data class TagEntity(
    val tag: String,
    val id: Int
)
