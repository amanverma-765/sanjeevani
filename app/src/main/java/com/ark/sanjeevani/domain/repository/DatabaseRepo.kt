package com.ark.sanjeevani.domain.repository

import com.ark.sanjeevani.domain.enums.HospitalType
import com.ark.sanjeevani.domain.model.BannerItem
import com.ark.sanjeevani.domain.model.Dietitian
import com.ark.sanjeevani.domain.model.Doctor
import com.ark.sanjeevani.domain.model.DoctorCategory
import com.ark.sanjeevani.domain.model.Hospital
import com.ark.sanjeevani.domain.model.HospitalRoom
import com.ark.sanjeevani.domain.model.Physiotherapist

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

    suspend fun getDoctorCategories(): Result<List<DoctorCategory>>

    suspend fun getDoctorsByCategory(categoryId: String): Result<List<Doctor>>

    suspend fun getPhysiotherapists(): Result<List<Physiotherapist>>

    suspend fun getDietitians(): Result<List<Dietitian>>
}