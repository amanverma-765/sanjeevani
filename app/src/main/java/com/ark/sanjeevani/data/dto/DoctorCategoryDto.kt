package com.ark.sanjeevani.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DoctorCategoryDto(
    val id: String,
    val name: String,
)