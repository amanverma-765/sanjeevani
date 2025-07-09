package com.ark.sanjeevani.presentation.features.onBoarding.logic

import com.ark.sanjeevani.domain.model.Language

data class LocalizationUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val languageResp: List<Language> = emptyList(),
    val selectedLanguage: Language? = null
)
