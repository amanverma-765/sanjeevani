package com.ark.sanjeevani.presentation.navigation

import com.ark.sanjeevani.domain.enums.HospitalType
import kotlinx.serialization.Serializable


sealed interface Destinations {

    @Serializable
    data object Localization : Destinations

    @Serializable
    data object Onboarding : Destinations

    @Serializable
    data object Home : Destinations

    @Serializable
    data class Hospital(val type: HospitalType) : Destinations

    @Serializable
    data class HospitalDetail(val id: String) : Destinations

    @Serializable
    data object Login : Destinations

    @Serializable
    data object Registration : Destinations

    @Serializable
    data object Notification : Destinations

    @Serializable
    data object Profile : Destinations
}