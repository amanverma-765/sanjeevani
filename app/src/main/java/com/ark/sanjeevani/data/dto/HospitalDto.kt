package com.ark.sanjeevani.data.dto

import com.ark.sanjeevani.domain.enums.HospitalType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HospitalDto(
    val id : String,
    val name : String,
    val lat : String,
    val lon : String,
    val address: String,
    val type: HospitalType,
    @SerialName("created_at")
    val createdAt: String,
    val img: String
)
