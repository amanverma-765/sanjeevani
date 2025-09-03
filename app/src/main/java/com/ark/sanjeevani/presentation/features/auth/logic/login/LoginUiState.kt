package com.ark.sanjeevani.presentation.features.auth.logic.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val isUserLoggedIn: Boolean = false
)
