package com.ark.sanjeevani.domain.repository

import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow

interface SupabaseRepo {
    fun listenAuthStatus(): Flow<ApiResponse<LoginUserInfo>>
}