package com.ark.sanjeevani.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HospitalRoomDto(
    val id: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("room_number")
    val roomNumber: Int,
    val name: String,
    val subtitle: String
)
