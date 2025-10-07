package com.ark.sanjeevani.presentation.features.user.home.logic

sealed interface HomeUiEvent {
    data object ClearError : HomeUiEvent
}