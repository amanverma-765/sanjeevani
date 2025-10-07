package com.ark.sanjeevani.presentation.features.individual.tab.logic

import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.ark.sanjeevani.domain.model.RegisteredUser

data class TabUiState(
    val isUserLoading: Boolean = false,
    val authError: String? = null,
    val userInfo: LoginUserInfo? = null,

    val registeredUser: RegisteredUser? = null,
    val registrationError: String? = null,

    val selectedDestination: TabDestinations
)