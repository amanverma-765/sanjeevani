package com.ark.sanjeevani.domain.model

data class BannerItem(
    val id: String,
    val createdAt: String,
    val description: String,
    val url: String,
    val title: String,
    val deeplink: String
)