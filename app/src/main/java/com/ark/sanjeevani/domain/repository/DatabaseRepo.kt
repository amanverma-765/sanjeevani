package com.ark.sanjeevani.domain.repository

import com.ark.sanjeevani.data.dto.HospitalRoomDto
import com.ark.sanjeevani.domain.enums.HospitalType
import com.ark.sanjeevani.domain.model.BannerItem
import com.ark.sanjeevani.domain.model.Hospital
import com.ark.sanjeevani.domain.model.HospitalRoom

interface DatabaseRepo {
    suspend fun getAllCities(): Result<List<String>>

    suspend fun getAllStates(): Result<List<String>>

    suspend fun getAllBanners(): Result<List<BannerItem>>

    suspend fun getAllHospitals(
        lat: String,
        lon: String,
        type: HospitalType
    ): Result<List<Hospital>>

    suspend fun getHospitalRooms(hospitalId: String): Result<List<HospitalRoom>>

    suspend fun getHospitalById(hospitalId: String): Result<Hospital>
}