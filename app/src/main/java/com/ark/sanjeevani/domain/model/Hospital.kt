package com.ark.sanjeevani.domain.model

import com.ark.sanjeevani.domain.enums.HospitalType

data class Hospital(
    val id : String,
    val name : String,
    val lat : String,
    val lon : String,
    val type: HospitalType,
    val createdAt: String,
    val img: String
)
