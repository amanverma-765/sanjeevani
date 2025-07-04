package com.ark.sanjeevani.domain

import com.skydoves.sandwich.ApiResponse
import io.github.jan.supabase.auth.user.UserInfo

interface SupabaseRepo {
    suspend fun getAuthenticatedUser(): ApiResponse<UserInfo>
}