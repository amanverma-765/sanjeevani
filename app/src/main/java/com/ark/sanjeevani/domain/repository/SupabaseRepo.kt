package com.ark.sanjeevani.domain.repository

import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.skydoves.sandwich.ApiResponse
import io.github.jan.supabase.auth.user.UserInfo

interface SupabaseRepo {
    suspend fun getAuthenticatedUser(): ApiResponse<LoginUserInfo>
}