package com.ark.sanjeevani.domain.repository

import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.ark.sanjeevani.domain.model.RegisteredUser
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationRepo {
    val authState: StateFlow<Result<LoginUserInfo?>>

    suspend fun loginWithGoogle(token: String): Result<Unit>

    suspend fun registerNewUser(registeredUser: RegisteredUser): Result<Unit>

    suspend fun getRegisteredUser(email: String): Result<RegisteredUser?>
}