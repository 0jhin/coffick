package com.yj.coffick.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


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
