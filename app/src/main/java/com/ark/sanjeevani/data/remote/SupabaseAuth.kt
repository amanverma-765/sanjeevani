package com.ark.sanjeevani.data.remote

import co.touchlab.kermit.Logger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.IDToken
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class SupabaseAuth(private val supabaseClient: SupabaseClient) {

    val logger = Logger.withTag("SupabaseAuth")
    fun listenAuthStatus(): Flow<Result<UserInfo>> {
        return supabaseClient.auth.sessionStatus.transform { status ->
            when (status) {
                SessionStatus.Initializing -> Unit
                is SessionStatus.NotAuthenticated -> emit(Result.failure(RuntimeException("No authenticated user found")))
                is SessionStatus.RefreshFailure -> emit(Result.failure(RuntimeException("Session refresh failed")))
                is SessionStatus.Authenticated -> {
                    status.session.user?.let { userInfo ->
                        emit(Result.success(userInfo))
                    }
                }
            }
        }
    }

    suspend fun loginWithGoogle(token: String, nonce: String): Result<Unit> {
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
}