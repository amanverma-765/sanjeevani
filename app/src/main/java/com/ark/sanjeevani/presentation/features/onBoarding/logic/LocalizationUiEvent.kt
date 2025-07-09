package com.ark.sanjeevani.presentation.features.onBoarding.logic

import com.ark.sanjeevani.domain.model.Language

sealed interface LocalizationUiEvent {
    data object ClearErrorMsg: LocalizationUiEvent
    data class SelectLanguage(val language: Language): LocalizationUiEvent
}