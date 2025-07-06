package com.ark.sanjeevani.presentation.features.onBoarding.logic

sealed interface LocalizationUiEvent {
    data object ClearErrorMsg: LocalizationUiEvent
    data class SelectLanguage(val language: Language): LocalizationUiEvent
}