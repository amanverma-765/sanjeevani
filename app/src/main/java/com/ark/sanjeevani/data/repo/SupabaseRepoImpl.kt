package com.ark.sanjeevani.data.repo


import com.ark.sanjeevani.data.remote.SupabaseDb
import com.ark.sanjeevani.domain.SupabaseRepo
import com.skydoves.sandwich.ApiResponse
import io.github.jan.supabase.auth.user.UserInfo

class SupabaseRepoImpl(private val supabaseDb: SupabaseDb): SupabaseRepo {
    override suspend fun getAuthenticatedUser(): ApiResponse<UserInfo> {
        return supabaseDb.getAuthenticatedUser()
    }
}