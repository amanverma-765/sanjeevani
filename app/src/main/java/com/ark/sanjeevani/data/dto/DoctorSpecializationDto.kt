package com.ark.sanjeevani.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DoctorSpecializationDto(
    @SerialName("doctor_id")
    val doctorId: String,
    @SerialName("specialization_id")
    val specializationId: String,
)
