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

    val logger = Logger.withTag("Supabase454545Auth")

    fun listenAuthStatus(): Flow<Result<UserInfo?>> {
        logger.i { "Authentication listener started..." }
        return supabaseClient.auth.sessionStatus.transform { status ->
            when (status) {
                is SessionStatus.NotAuthenticated -> {
                    emit(Result.failure(RuntimeException("No authenticated user found")))
                    logger.e { "No authenticated user found" }
                }
                is SessionStatus.RefreshFailure -> {
                    emit(Result.failure(RuntimeException("Session refresh failed")))
                    logger.e { "Failed to refresh auth token" }
                }
                is SessionStatus.Authenticated -> {
                    status.session.user?.let { userInfo ->
                        logger.i { "User info: $userInfo" }
                        emit(Result.success(userInfo))
                    }
                }
                SessionStatus.Initializing -> {
                    logger.i { "Loading the auth state" }
                    emit(Result.success(null))
                }
            }
        }
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
}