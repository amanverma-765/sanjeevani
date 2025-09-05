package com.ark.sanjeevani.domain.repository

import com.ark.sanjeevani.domain.model.RegisteredUser

interface DatabaseRepo {
    suspend fun getAllCities(): Result<List<String>>
    suspend fun getAllStates(): Result<List<String>>
    suspend fun registerNewUser(registeredUser: RegisteredUser): Result<Unit>
    suspend fun getRegisteredUser(email: String): Result<RegisteredUser?>
}