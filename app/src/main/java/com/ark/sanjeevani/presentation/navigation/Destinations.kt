package com.ark.sanjeevani.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed interface Destinations {

    data object OnBoarding:  Destinations

    data object Home:  Destinations

    data object Login:  Destinations

    data class Registration(val isProfessional: Boolean):  Destinations


}