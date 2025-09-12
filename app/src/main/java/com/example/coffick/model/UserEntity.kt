package com.example.coffick.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date
import kotlin.time.ExperimentalTime


@Serializable
data class UserEntity constructor(
    val id: String,
    val nickname: String?,
    @SerialName("blocked_at") val blockedAt: String?,
    val blockReason: String
) {
//    fun getBlockedAtTime() : Date {
//        //this.blockedAt.
//    }
}
// yyyy-MM-ddT
