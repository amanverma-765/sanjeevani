package com.ark.sanjeevani.presentation.features.home.logic

import com.ark.sanjeevani.domain.model.BannerItem
import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.ark.sanjeevani.domain.model.Service

data class HomeUiState(
    val isUserLoading: Boolean = false,
    val isBannerLoading: Boolean = false,
    val isServicesLoading: Boolean = false,
    val errorMsg: String? = null,
    val userInfo: LoginUserInfo? = null,
    val banners: List<BannerItem> = emptyList(),
    val services: List<Service> = emptyList()
)