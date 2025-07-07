package com.ark.sanjeevani.data.repo


import com.ark.sanjeevani.data.mapper.UserInfoDtoMapper.toLoginUserInfo
import com.ark.sanjeevani.data.remote.SupabaseDb
import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.ark.sanjeevani.domain.repository.SupabaseRepo
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.mapSuccess

class SupabaseRepoImpl(private val supabaseDb: SupabaseDb): SupabaseRepo {
    override suspend fun getAuthenticatedUser(): ApiResponse<LoginUserInfo> {
        return supabaseDb.getAuthenticatedUser().mapSuccess {
            this.toLoginUserInfo()
        }
    }
}