package com.ark.sanjeevani.data.remote

import co.touchlab.kermit.Logger
import com.ark.sanjeevani.data.dto.RegisteredUserDto
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SupabaseAuth(private val supabaseClient: SupabaseClient) {

    val logger = Logger.withTag("SupabaseAuth")

    private val _authState = MutableStateFlow<Result<UserInfo?>>(Result.success(null))
    val authState: StateFlow<Result<UserInfo?>> get() = _authState

    init {
        logger.i { "Authentication listener started..." }
        supabaseClient.auth.sessionStatus
            .onEach { status ->
                when (status) {
                    is SessionStatus.NotAuthenticated -> {
                        _authState.value = Result.failure(RuntimeException("No authenticated user found"))
                        logger.e { "No authenticated user found" }
                    }
                    is SessionStatus.RefreshFailure -> {
                        _authState.value = Result.failure(RuntimeException("Session refresh failed"))
                        logger.e { "Failed to refresh auth token" }
                    }
                    is SessionStatus.Authenticated -> {
                        status.session.user?.let { userInfo ->
                            logger.i { "User info: $userInfo" }
                            _authState.value = Result.success(userInfo)
                        }
                    }
                    SessionStatus.Initializing -> {
                        logger.i { "Loading the auth state" }
                        _authState.value = Result.success(null)
                    }
                }
            }
            .launchIn(CoroutineScope(SupervisorJob() + Dispatchers.IO))
    }

    suspend fun loginWithGoogle(token: String): Result<Unit> {
        return try {
            supabaseClient.auth.signInWith(IDToken) {
                idToken = token
                provider = Google
            }
            return Result.success(Unit)
        } catch (e: Exception) {
            logger.e(e) { "Failed to login with google" }
            Result.failure(e)
        }
    }

    suspend fun registerNewUser(registeredUserDto: RegisteredUserDto): Result<Unit> {
        return try {
            supabaseClient
                .from("user")
                .insert(registeredUserDto)
            return Result.success(Unit)
        } catch (e: Exception) {
            logger.e(e) { "Error while registering new user: ${e.message}" }
            Result.failure(RuntimeException("Something went wrong, try again"))
        }
    }

    suspend fun getRegisteredUser(email: String): Result<RegisteredUserDto?> {
        return try {
            val user = supabaseClient
                .from("user")
                .select {
                    filter {
                        eq("email", email)
                    }
                }
                .decodeList<RegisteredUserDto>()
            if (user.isEmpty()) Result.failure(RuntimeException("User is not registered")) else Result.success(user.first())
        } catch (e: Exception) {
            logger.e(e) { "Error while fetching registered user: ${e.message}" }
            Result.failure(RuntimeException("Something went wrong, try again"))
        }
    }
}