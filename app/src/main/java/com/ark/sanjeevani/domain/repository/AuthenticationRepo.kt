package com.ark.sanjeevani.domain.repository

import com.ark.sanjeevani.domain.model.LoginUserInfo
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationRepo {
    val authState: StateFlow<Result<LoginUserInfo?>>

    suspend fun loginWithGoogle(token: String): Result<Unit>

//    fun loginWithFb(): Result<Unit>
}