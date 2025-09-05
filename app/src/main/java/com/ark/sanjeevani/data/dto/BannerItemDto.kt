package com.ark.sanjeevani.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BannerItemDto(
    val id: String,
    @SerialName("created_at")
    val createdAt: String,
    val description: String,
    val url: String,
    val title: String,
    val deeplink: String
)