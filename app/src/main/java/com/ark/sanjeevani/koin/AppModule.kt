package com.ark.sanjeevani.koin

import com.ark.sanjeevani.presentation.features.auth.logic.AuthViewModel
import com.ark.sanjeevani.data.remote.SupabaseDb
import com.ark.sanjeevani.data.repo.SupabaseRepoImpl
import com.ark.sanjeevani.domain.repository.SupabaseRepo
import com.ark.sanjeevani.presentation.features.auth.logic.RegistrationViewModel
import com.ark.sanjeevani.presentation.features.home.logic.HomeViewModel
import com.ark.sanjeevani.presentation.features.onBoarding.logic.LocalizationViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::LocalizationViewModel)
    singleOf(::SupabaseRepoImpl) { bind<SupabaseRepo>() }
    singleOf(::SupabaseDb)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::RegistrationViewModel)
}