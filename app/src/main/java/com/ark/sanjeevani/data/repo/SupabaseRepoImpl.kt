package com.ark.sanjeevani.data.repo


import com.ark.sanjeevani.data.mapper.UserInfoDtoMapper.toLoginUserInfo
import com.ark.sanjeevani.data.remote.SupabaseDb
import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.ark.sanjeevani.domain.repository.SupabaseRepo
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.mapSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SupabaseRepoImpl(private val supabaseDb: SupabaseDb) : SupabaseRepo {
    override fun listenAuthStatus(): Flow<ApiResponse<LoginUserInfo>> {
        return supabaseDb.listenAuthStatus()
            .map { response -> response.mapSuccess { this.toLoginUserInfo() } }
    }

}