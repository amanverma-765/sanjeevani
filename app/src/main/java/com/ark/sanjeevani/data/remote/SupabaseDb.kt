package com.ark.sanjeevani.data.remote

import co.touchlab.kermit.Logger
import com.ark.sanjeevani.data.dto.CityDto
import com.ark.sanjeevani.data.dto.RegisteredUserDto
import com.ark.sanjeevani.data.dto.StatesDto
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

    suspend fun registerNewUser(registeredUserDto: RegisteredUserDto): Result<Unit> {
        return try {
            supabaseClient
                .from("user")
                .insert(registeredUserDto)
            return Result.success(Unit)
        } catch (e: Exception) {
            logger.e(e) { "Error while registering new user: ${e.message}" }
            Result.failure(RuntimeException("Something went wrong, try again"))
        }
    }

    suspend fun getRegisteredUser(email: String): Result<RegisteredUserDto?> {
        return try {
            val user = supabaseClient
                .from("user")
                .select {
                    filter {
                        eq("email", email)
                    }
                }
                .decodeList<RegisteredUserDto>()
            if (user.isEmpty()) Result.success(null) else Result.success(user.first())
        } catch (e: Exception) {
            logger.e(e) { "Error while fetching registered user: ${e.message}" }
            Result.failure(RuntimeException("Something went wrong, try again"))
        }
    }


}