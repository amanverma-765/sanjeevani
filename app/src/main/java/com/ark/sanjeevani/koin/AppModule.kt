package com.ark.sanjeevani.koin

import com.ark.sanjeevani.data.remote.SupabaseAuth
import com.ark.sanjeevani.data.remote.SupabaseDb
import com.ark.sanjeevani.data.repo.AuthRepoImpl
import com.ark.sanjeevani.data.repo.DbRepoImpl
import com.ark.sanjeevani.domain.repository.AuthenticationRepo
import com.ark.sanjeevani.domain.repository.DatabaseRepo
import com.ark.sanjeevani.presentation.features.auth.logic.login.LoginViewModel
import com.ark.sanjeevani.presentation.features.auth.logic.reg.RegistrationViewModel
import com.ark.sanjeevani.presentation.features.home.logic.HomeViewModel
import com.ark.sanjeevani.presentation.features.hospital.logic.HospitalViewModel
import com.ark.sanjeevani.presentation.features.onBoarding.logic.LocalizationViewModel
import com.ark.sanjeevani.presentation.features.tabs.logic.TabViewModel
import com.ark.sanjeevani.utils.NetworkViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single<CoroutineScope> {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    viewModelOf(::LocalizationViewModel)
    singleOf(::AuthRepoImpl) { bind<AuthenticationRepo>() }
    singleOf(::SupabaseAuth)
    viewModelOf(::HomeViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegistrationViewModel)
    singleOf(::SupabaseDb)
    singleOf(::DbRepoImpl) { bind<DatabaseRepo>()}
    viewModelOf(::NetworkViewModel)
    viewModelOf(::HospitalViewModel)
    viewModelOf(::TabViewModel)
}