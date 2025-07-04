package com.ark.sanjeevani.presentation.features.home.logic

import io.github.jan.supabase.auth.user.UserInfo

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val userInfo: UserInfo? = null
)