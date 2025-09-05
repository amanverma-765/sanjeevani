package com.ark.sanjeevani.domain.repository

import com.ark.sanjeevani.domain.model.BannerItem

interface DatabaseRepo {
    suspend fun getAllCities(): Result<List<String>>
    suspend fun getAllStates(): Result<List<String>>

    suspend fun getAllBanners(): Result<List<BannerItem>>
}