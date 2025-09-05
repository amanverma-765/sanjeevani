package com.ark.sanjeevani.presentation.features.auth.logic.login

import com.ark.sanjeevani.domain.model.LoginUserInfo

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val userState: LoginUserInfo? = null
)
