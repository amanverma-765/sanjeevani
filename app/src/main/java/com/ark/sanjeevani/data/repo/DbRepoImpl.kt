package com.ark.sanjeevani.data.repo

import com.ark.sanjeevani.data.mapper.BannerItemMapper.toBannerItem
import com.ark.sanjeevani.data.mapper.DoctorMapper.toDoctor
import com.ark.sanjeevani.data.mapper.DoctorMapper.toDoctorCategory
import com.ark.sanjeevani.data.mapper.HospitalMapper.toHospital
import com.ark.sanjeevani.data.mapper.HospitalMapper.toHospitalRoom
import com.ark.sanjeevani.data.remote.SupabaseDb
import com.ark.sanjeevani.domain.enums.HospitalType
import com.ark.sanjeevani.domain.model.BannerItem
import com.ark.sanjeevani.domain.model.Doctor
import com.ark.sanjeevani.domain.model.DoctorCategory
import com.ark.sanjeevani.domain.model.Hospital
import com.ark.sanjeevani.domain.model.HospitalRoom
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

    override suspend fun getAllHospitals(
        lat: String,
        lon: String,
        type: HospitalType
    ): Result<List<Hospital>> {
        return supabaseDb.getAllHospitals(lat, lon, type).map { result ->
            result.map { it.toHospital() }
        }
    }

    override suspend fun getHospitalRooms(hospitalId: String): Result<List<HospitalRoom>> {
        return supabaseDb.getHospitalRooms(hospitalId).map { result ->
            result.map { it.toHospitalRoom() }
        }
    }

    override suspend fun getHospitalById(hospitalId: String): Result<Hospital> {
        return supabaseDb.getHospitalById(hospitalId).map { it.toHospital() }
    }

    override suspend fun getDoctorCategories(): Result<List<DoctorCategory>> {
        return supabaseDb.getDoctorCategories().map { result ->
            result.map {
                it.toDoctorCategory()
            }
        }
    }

    override suspend fun getDoctorsByCategory(categoryId: String): Result<List<Doctor>> {
        return supabaseDb.getDoctorsByCategory(categoryId).map { result ->
            result.map {
                it.toDoctor()
            }
        }
    }
}