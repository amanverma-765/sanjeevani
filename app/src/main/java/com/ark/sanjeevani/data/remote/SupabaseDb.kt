package com.ark.sanjeevani.data.remote

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.ApiResponse.*
import com.skydoves.sandwich.ApiResponse.Failure.*
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class SupabaseDb(private val supabaseClient: SupabaseClient) {
    fun listenAuthStatus(): Flow<ApiResponse<UserInfo>> {
        return supabaseClient.auth.sessionStatus.transform { status ->
            when (status) {
                SessionStatus.Initializing -> Unit
                is SessionStatus.NotAuthenticated -> emit(Error("No authenticated user found"))
                is SessionStatus.RefreshFailure -> emit(Error("Something went wrong"))
                is SessionStatus.Authenticated -> {
                    status.session.user?.let { userInfo ->
                        emit(Success(userInfo))
                    }
                }
            }
        }
    }
}