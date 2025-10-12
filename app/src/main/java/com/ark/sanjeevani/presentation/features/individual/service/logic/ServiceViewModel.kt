package com.ark.sanjeevani.presentation.features.individual.service.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.domain.repository.DatabaseRepo
import com.ark.sanjeevani.presentation.features.individual.home.logic.ServiceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServiceViewModel(private val databaseRepo: DatabaseRepo) : ViewModel() {
    val logger = Logger.withTag("HospitalViewModel")

    private val _uiState = MutableStateFlow(ServiceUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ServiceUiEvent) {
        when (event) {
            ServiceUiEvent.ClearError -> _uiState.update { it.copy(errorMsg = null) }
            is ServiceUiEvent.GetDoctorList -> getDoctorList(event.categoryId)
            is ServiceUiEvent.GetService -> {
                when (event.type) {
                    ServiceType.DOCTOR -> getDoctorCategories()
                    ServiceType.PHYSIOTHERAPIST -> getPhysiotherapists()
                    ServiceType.DIETITIAN -> getDietitians()
                    else -> {}
                }
            }
        }
    }

    private fun getPhysiotherapists() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                databaseRepo.getPhysiotherapists()
                    .onSuccess { physiotherapists ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                physiotherapists = physiotherapists
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
                logger.e("Error fetching physiotherapists: ${e.localizedMessage}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMsg = e.localizedMessage ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }

    private fun getDietitians() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                databaseRepo.getDietitians()
                    .onSuccess { dietitians ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                dietitians = dietitians
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
                logger.e("Error fetching dietitians: ${e.localizedMessage}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMsg = e.localizedMessage ?: "An unexpected error occurred"
                    )
                }
            }
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