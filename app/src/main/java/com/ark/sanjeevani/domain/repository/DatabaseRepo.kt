package com.ark.sanjeevani.domain.repository

interface DatabaseRepo {
    suspend fun getAllCities(): Result<List<String>>
    suspend fun getAllStates(): Result<List<String>>
    suspend fun registerNewUser(): Result<Unit>
}