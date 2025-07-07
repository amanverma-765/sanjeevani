package com.ark.sanjeevani.presentation.features.home.logic

import com.ark.sanjeevani.domain.model.LoginUserInfo

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val userInfo: LoginUserInfo? = null
)