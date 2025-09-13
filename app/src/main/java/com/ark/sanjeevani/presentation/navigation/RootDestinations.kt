package com.ark.sanjeevani.presentation.navigation

import com.ark.sanjeevani.domain.enums.HospitalType
import kotlinx.serialization.Serializable


sealed interface RootDestinations {

    @Serializable
    data class Tab(val initialTab: String?) : RootDestinations

    @Serializable
    data object Localization : RootDestinations

    @Serializable
    data object Onboarding : RootDestinations

    @Serializable
    data class Hospital(val type: HospitalType) : RootDestinations

    @Serializable
    data class HospitalDetail(val id: String) : RootDestinations

    @Serializable
    data object Login : RootDestinations

    @Serializable
    data object Registration : RootDestinations

    @Serializable
    data object Notification : RootDestinations

    @Serializable
    data object Profile : RootDestinations
}