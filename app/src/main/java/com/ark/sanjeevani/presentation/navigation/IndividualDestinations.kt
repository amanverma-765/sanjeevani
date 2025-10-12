package com.ark.sanjeevani.presentation.navigation

import com.ark.sanjeevani.domain.enums.HospitalType
import kotlinx.serialization.Serializable


sealed interface IndividualDestinations {

    @Serializable
    data class Tab(val initialTab: String?) : IndividualDestinations

    @Serializable
    data object Localization : IndividualDestinations

    @Serializable
    data object Onboarding : IndividualDestinations

    @Serializable
    data class HospitalList(val type: HospitalType) : IndividualDestinations

    @Serializable
    data class HospitalDetail(val id: String) : IndividualDestinations

    @Serializable
    data object Login : IndividualDestinations

    @Serializable
    data object Registration : IndividualDestinations

    @Serializable
    data object Notification : IndividualDestinations

    @Serializable
    data object Profile : IndividualDestinations

    @Serializable
    data class DoctorList(val id: String, val cat: String) : IndividualDestinations

    @Serializable
    data object DoctorCategory : IndividualDestinations

    @Serializable
    data object DoctorDetail : IndividualDestinations
}