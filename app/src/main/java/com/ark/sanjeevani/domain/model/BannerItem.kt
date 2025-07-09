package com.ark.sanjeevani.domain.model

import kotlin.random.Random

data class BannerItem(
    val id: String,
    val imageUrl: String,
    val description: String? = null
)

val mockBanners = (1..5).map {
    val imgId = Random.nextInt(100, 901)
    BannerItem(
        id = it.toString(),
        imageUrl = "https://picsum.photos/id/$imgId/500/300",
        description = "Banner $it"
    )
}