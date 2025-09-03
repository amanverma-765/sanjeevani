package com.ark.sanjeevani.data.repo


import com.ark.sanjeevani.data.mapper.UserInfoDtoMapper.toLoginUserInfo
import com.ark.sanjeevani.data.remote.SupabaseAuth
import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.ark.sanjeevani.domain.repository.AuthenticationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepoImpl(private val supabaseAuth: SupabaseAuth) : AuthenticationRepo {
    override fun listenAuthStatus(): Flow<Result<LoginUserInfo>> {
        return supabaseAuth.listenAuthStatus()
            .map { response -> response.map { it.toLoginUserInfo() } }
    }

    override suspend fun loginWithGoogle(
        token: String,
        nonce: String
    ): Result<Unit> {
        return supabaseAuth.loginWithGoogle(token, nonce)
    }
}