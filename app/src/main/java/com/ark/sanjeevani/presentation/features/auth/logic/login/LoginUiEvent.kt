package com.ark.sanjeevani.presentation.features.auth.logic.login

import android.content.Context

sealed interface LoginUiEvent {
    data object ClearErrorMsg : LoginUiEvent
    data class LoginWithGoogle(val context: Context) : LoginUiEvent
    data class LoginWithFb(val token: String) : LoginUiEvent
}