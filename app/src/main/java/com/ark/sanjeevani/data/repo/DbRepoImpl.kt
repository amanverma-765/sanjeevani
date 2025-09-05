package com.ark.sanjeevani.data.repo

import com.ark.sanjeevani.data.mapper.BannerItemMapper.toBannerItem
import com.ark.sanjeevani.data.remote.SupabaseDb
import com.ark.sanjeevani.domain.model.BannerItem
import com.ark.sanjeevani.domain.repository.DatabaseRepo

class DbRepoImpl(private val supabaseDb: SupabaseDb) : DatabaseRepo {
    override suspend fun getAllCities(): Result<List<String>> {
        return supabaseDb.getAllCities()
    }

    override suspend fun getAllStates(): Result<List<String>> {
        return supabaseDb.getAllStates()
    }

    override suspend fun getAllBanners(): Result<List<BannerItem>> {
        return supabaseDb.getAllBanners().map { result ->
            result.map { it.toBannerItem() }
        }
    }
}