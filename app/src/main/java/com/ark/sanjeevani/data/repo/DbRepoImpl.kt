package com.ark.sanjeevani.data.repo

import com.ark.sanjeevani.data.mapper.RegisteredUserMapper.toRegisteredUser
import com.ark.sanjeevani.data.mapper.RegisteredUserMapper.toRegisteredUserDto
import com.ark.sanjeevani.data.remote.SupabaseDb
import com.ark.sanjeevani.domain.model.RegisteredUser
import com.ark.sanjeevani.domain.repository.DatabaseRepo

class DbRepoImpl(private val supabaseDb: SupabaseDb) : DatabaseRepo {
    override suspend fun getAllCities(): Result<List<String>> {
        return supabaseDb.getAllCities()
    }

    override suspend fun getAllStates(): Result<List<String>> {
        return supabaseDb.getAllStates()
    }

    override suspend fun registerNewUser(registeredUser: RegisteredUser): Result<Unit> {
        return supabaseDb.registerNewUser(registeredUser.toRegisteredUserDto())
    }

    override suspend fun getRegisteredUser(email: String): Result<RegisteredUser?> {
        return supabaseDb.getRegisteredUser(email).map { it?.toRegisteredUser() }
    }

}