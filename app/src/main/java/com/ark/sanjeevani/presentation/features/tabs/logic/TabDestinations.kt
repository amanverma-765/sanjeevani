package com.ark.sanjeevani.presentation.features.tabs.logic

import com.ark.sanjeevani.presentation.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
sealed class TabDestinations: Destination {
    @Serializable
    data object Home : TabDestinations()

    @Serializable
    data object History : TabDestinations()
}