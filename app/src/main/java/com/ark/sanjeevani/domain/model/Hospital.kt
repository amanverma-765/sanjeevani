package com.ark.sanjeevani.domain.model

import com.ark.sanjeevani.domain.enums.HospitalType

data class Hospital(
    val id : String,
    val name : String,
    val lat : String,
    val address: String,
    val lon : String,
    val type: HospitalType,
    val createdAt: String,
    val img: String,
    val rating: Double,
    val website: String?,
)
