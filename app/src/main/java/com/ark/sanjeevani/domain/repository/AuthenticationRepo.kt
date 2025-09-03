package com.ark.sanjeevani.domain.repository

import com.ark.sanjeevani.domain.model.LoginUserInfo
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepo {
    fun listenAuthStatus(): Flow<Result<LoginUserInfo?>>

    suspend fun loginWithGoogle(token: String): Result<Unit>

//    fun loginWithFb(): Result<Unit>
}