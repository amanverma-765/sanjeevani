package com.ark.sanjeevani.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: String,
    val name: String
)