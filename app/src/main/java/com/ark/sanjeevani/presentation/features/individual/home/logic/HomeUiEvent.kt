package com.ark.sanjeevani.presentation.features.individual.home.logic

sealed interface HomeUiEvent {
    data object ClearError : HomeUiEvent
}