package com.ark.sanjeevani.data.remote

import co.touchlab.kermit.Logger
import com.skydoves.sandwich.ApiResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.user.UserInfo

class SupabaseDb(private val supabaseClient: SupabaseClient) {

    fun getAuthenticatedUser(): ApiResponse<UserInfo> {
        try {
            val userInfo = supabaseClient.auth.currentUserOrNull()
            if (userInfo == null) return ApiResponse.Failure.Error("No authenticated user found.")
            return ApiResponse.Success(userInfo)
        } catch (e: Exception) {
            Logger.e( "Error fetching authenticated user: ${e.message}", e)
            return ApiResponse.Failure.Exception(e)
        }
    }
}