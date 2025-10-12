package com.ark.sanjeevani.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DietitianDto(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val avatar: String,
    @SerialName("experience_months")
    val experienceMonths: Int,
)