package com.ark.sanjeevani.presentation.features.individual.home.logic

import com.ark.sanjeevani.domain.model.BannerItem

data class HomeUiState(
    val errorMsg: String? = null,
    val banners: List<BannerItem> = emptyList(),
    val isBannerLoading: Boolean = false,
    val bannerError: String? = null
)