package com.ark.sanjeevani.presentation.features.individual.tab.logic

sealed interface TabUiEvent {
    data object ClearError : TabUiEvent
    data class GetRegisteredUser(val email: String) : TabUiEvent
    data class SelectDestination(val destination: TabDestinations) : TabUiEvent
}