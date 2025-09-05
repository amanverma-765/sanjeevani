package com.ark.sanjeevani.data.mapper

import com.ark.sanjeevani.data.dto.BannerItemDto
import com.ark.sanjeevani.domain.model.BannerItem

object BannerItemMapper {
    fun BannerItemDto.toBannerItem(): BannerItem {
        return BannerItem(
            id = id,
            createdAt = createdAt,
            description = description,
            url = url,
            title = title,
            deeplink = deeplink
        )
    }
}