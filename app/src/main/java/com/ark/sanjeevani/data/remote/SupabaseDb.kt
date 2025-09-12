package com.ark.sanjeevani.data.remote

import co.touchlab.kermit.Logger
import com.ark.sanjeevani.data.dto.BannerItemDto
import com.ark.sanjeevani.data.dto.CityDto
import com.ark.sanjeevani.data.dto.HospitalDto
import com.ark.sanjeevani.data.dto.HospitalRoomDto
import com.ark.sanjeevani.data.dto.StatesDto
import com.ark.sanjeevani.domain.enums.HospitalType
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class SupabaseDb(private val supabaseClient: SupabaseClient) {

    val logger = Logger.withTag("SupabaseDb")

    suspend fun getAllCities(): Result<List<String>> {
        return try {
            val response = supabaseClient
                .from("city")
                .select() {
                    order("name", Order.ASCENDING)
                }
                .decodeList<CityDto>()

            if (response.isEmpty()) {
                logger.e { "No cities found" }
                Result.failure(RuntimeException("No cities found"))
            } else {
                logger.i { "Cities fetched successfully: $response" }
                Result.success(response.map { it.name })
            }
        } catch (e: Exception) {
            logger.e(e) { "Error fetching cities: ${e.message}" }
            Result.failure(RuntimeException("Something went wrong, try again"))
        }
    }

    suspend fun getAllStates(): Result<List<String>> {
        return try {
            val response = supabaseClient
                .from("states")
                .select() {
                    order("name", Order.ASCENDING)
                }
                .decodeList<StatesDto>()

            if (response.isEmpty()) {
                logger.e { "No states found" }
                Result.failure(RuntimeException("No states found"))
            } else {
                logger.i { "Cities fetched successfully: $response" }
                Result.success(response.map { it.name })
            }
        } catch (e: Exception) {
            logger.e(e) { "Error fetching cities: ${e.message}" }
            Result.failure(RuntimeException("Something went wrong, try again"))
        }
    }

    suspend fun getAllBanners(): Result<List<BannerItemDto>> {
        return try {
            val response = supabaseClient
                .from("banners")
                .select()
                .decodeList<BannerItemDto>()

            if (response.isEmpty()) {
                logger.e { "No banners found" }
                Result.failure(RuntimeException("No banners found"))
            } else {
                logger.i { "Banners fetched successfully: $response" }
                Result.success(response)
            }
        } catch (e: Exception) {
            logger.e(e) { "Error fetching banners: ${e.message}" }
            Result.failure(RuntimeException("Something went wrong, try again"))
        }
    }

    suspend fun getAllHospitals(
        lat: String, // currently not used
        lon: String, // currently not used
        type: HospitalType
    ): Result<List<HospitalDto>> {
        return try {
            val response = supabaseClient
                .from("hospitals")
                .select {
                    filter {
                        eq("type", type.name)
                    }
                }
                .decodeList<HospitalDto>()

            logger.i { "Hospitals fetched successfully: $response" }
            Result.success(response)
        } catch (e: Exception) {
            logger.e(e) { "Error fetching hospitals: ${e.message}" }
            Result.failure(RuntimeException("Something went wrong, try again"))
        }
    }

    suspend fun getHospitalById(hospitalId: String): Result<HospitalDto> {
        return try {
            val response = supabaseClient
                .from("hospitals")
                .select {
                    filter {
                        eq("id", hospitalId)
                    }
                }
                .decodeList<HospitalDto>()

            logger.i { "Hospital fetched successfully: ${response[0]}" }
            Result.success(response[0])
        } catch (e: Exception) {
            logger.e(e) { "Error fetching hospital: ${e.message}" }
            Result.failure(RuntimeException("Something went wrong, try again"))
        }
    }

    suspend fun getHospitalRooms(hospitalId: String): Result<List<HospitalRoomDto>> {
        return try {
            val response = supabaseClient
                .from("hospital_rooms")
                .select {
                    filter {
                        eq("hospital_id", hospitalId)
                    }
                    order("room_number", Order.ASCENDING)
                }
                .decodeList<HospitalRoomDto>()

            logger.i { "Hospital rooms fetched successfully: $response" }
            Result.success(response)
        } catch (e: Exception) {
            logger.e(e) { "Error fetching hospital rooms: ${e.message}" }
            Result.failure(RuntimeException("Something went wrong, try again"))
        }
    }
}