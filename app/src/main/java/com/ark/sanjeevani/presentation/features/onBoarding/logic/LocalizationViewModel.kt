package com.ark.sanjeevani.presentation.features.onBoarding.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ark.sanjeevani.domain.model.Language
import com.ark.sanjeevani.domain.model.mockLanguages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocalizationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LocalizationUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LocalizationUiEvent) {
        when (event) {
            LocalizationUiEvent.ClearErrorMsg -> _uiState.update { it.copy(errorMsg = null) }
            is LocalizationUiEvent.SelectLanguage -> selectLanguage(event.language)
        }
    }

    init {
        getAllLanguages()
    }

    private fun selectLanguage(language: Language) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // language selection logic
            val updatedLanguages = mockLanguages.map {
                if (it.id == language.id) {
                    it.copy(selected = true)
                } else {
                    it.copy(selected = false)
                }
            }
            _uiState.update {
                it.copy(
                    isLoading = false,
                    selectedLanguage = language,
                    languageResp = updatedLanguages
                )
            }
        }
    }


    private fun getAllLanguages() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // language retrieval logic
            _uiState.update { it.copy(isLoading = false, languageResp = mockLanguages) }
        }
    }

}