package com.ark.sanjeevani.presentation.navigation

import kotlinx.serialization.Serializable


sealed interface Destinations {

    @Serializable
    data object Localization : Destinations

    @Serializable
    data object Home : Destinations

    @Serializable
    data object Login : Destinations

    @Serializable
    data class Registration(val isProfessional: Boolean) : Destinations

    @Serializable
    data object Notification : Destinations
}