package com.ark.sanjeevani.presentation.features.home.logic

sealed interface HomeUiEvent {
    data object ClearErrorMsg : HomeUiEvent
    data class GetRegisteredUser(val email: String) : HomeUiEvent
}