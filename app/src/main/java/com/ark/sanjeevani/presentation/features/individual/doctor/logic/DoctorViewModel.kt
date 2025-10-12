package com.ark.sanjeevani.presentation.features.individual.doctor.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.domain.repository.DatabaseRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DoctorViewModel(private val databaseRepo: DatabaseRepo) : ViewModel() {
    val logger = Logger.withTag("HospitalViewModel")

    private val _uiState = MutableStateFlow(DoctorUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: DoctorUiEvent) {
        when (event) {
            DoctorUiEvent.ClearError -> _uiState.update { it.copy(errorMsg = null) }
            DoctorUiEvent.GetDoctorCategories -> getDoctorCategories()
            is DoctorUiEvent.GetDoctorList -> getDoctorList(event.categoryId)
        }
    }

    private fun getDoctorList(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                databaseRepo.getDoctorsByCategory(id)
                    .onSuccess { doctors ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                doctors = doctors
                            )
                        }
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMsg = error.localizedMessage ?: "An unexpected error occurred"
                            )
                        }
                    }
            } catch (e: Exception) {
                logger.e("Error fetching doctors: ${e.localizedMessage}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMsg = e.localizedMessage ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }

    private fun getDoctorCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val categories = databaseRepo.getDoctorCategories()
                    .onSuccess { categories ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                doctorCategories = categories
                            )
                        }
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMsg = error.localizedMessage ?: "An unexpected error occurred"
                            )
                        }
                    }
            } catch (e: Exception) {
                logger.e("Error fetching doctor categories: ${e.localizedMessage}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMsg = e.localizedMessage ?: "An unexpected error occurred"
                    )
                }
            }
        }

    }
}