package com.ark.sanjeevani.data.repo


import com.ark.sanjeevani.data.mapper.RegisteredUserMapper.toRegisteredUser
import com.ark.sanjeevani.data.mapper.RegisteredUserMapper.toRegisteredUserDto
import com.ark.sanjeevani.data.mapper.UserInfoDtoMapper.toLoginUserInfo
import com.ark.sanjeevani.data.remote.SupabaseAuth
import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.ark.sanjeevani.domain.model.RegisteredUser
import com.ark.sanjeevani.domain.repository.AuthenticationRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AuthRepoImpl(
    private val supabaseAuth: SupabaseAuth,
    externalScope: CoroutineScope
) : AuthenticationRepo {

    override val authState: StateFlow<Result<LoginUserInfo?>> =
        supabaseAuth.authState
            .map { result ->
                result.fold(
                    onSuccess = { user -> Result.success(user?.toLoginUserInfo()) },
                    onFailure = { e -> Result.failure(e) }
                )
            }
            .stateIn(
                scope = externalScope,
                started = SharingStarted.Eagerly,
                initialValue = Result.success(null)
            )

    override suspend fun loginWithGoogle(token: String): Result<Unit> {
        return supabaseAuth.loginWithGoogle(token)
    }

    override suspend fun registerNewUser(registeredUser: RegisteredUser): Result<Unit> {
        return supabaseAuth.registerNewUser(registeredUser.toRegisteredUserDto())
    }

    override suspend fun getRegisteredUser(email: String): Result<RegisteredUser?> {
        return supabaseAuth.getRegisteredUser(email).map { it?.toRegisteredUser() }
    }
}