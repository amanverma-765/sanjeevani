package com.ark.sanjeevani.presentation.features.home.logic

import com.ark.sanjeevani.domain.model.BannerItem
import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.ark.sanjeevani.domain.model.RegisteredUser
import com.ark.sanjeevani.domain.model.Service

data class HomeUiState(
    val isUserLoading: Boolean = false,
    val isServicesLoading: Boolean = false,
    val errorMsg: String? = null,
    val userInfo: LoginUserInfo? = null,

    val banners: List<BannerItem> = emptyList(),
    val isBannerLoading: Boolean = false,
    val bannerError: String? = null,

    val services: List<Service> = emptyList(),
    val registeredUser: RegisteredUser? = null,
    val registrationError: String? = null,
    val authError: String? = null
)