package com.ark.sanjeevani.presentation.features.onBoarding.logic

data class LocalizationUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val languageResp: List<Language> = emptyList()
)
