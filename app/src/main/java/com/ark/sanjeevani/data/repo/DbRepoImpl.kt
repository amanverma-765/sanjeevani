package com.ark.sanjeevani.data.repo

import com.ark.sanjeevani.data.remote.SupabaseDb
import com.ark.sanjeevani.domain.repository.DatabaseRepo

class DbRepoImpl(private val supabaseDb: SupabaseDb): DatabaseRepo {
    override suspend fun getAllCities(): Result<List<String>> {
        return supabaseDb.getAllCities()
    }

    override suspend fun getAllStates(): Result<List<String>> {
        return supabaseDb.getAllStates()
    }

    override suspend fun registerNewUser(): Result<Unit> {
        TODO("Not yet implemented")
    }

}