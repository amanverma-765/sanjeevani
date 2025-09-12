package com.ark.sanjeevani.presentation.features.hospital.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.domain.enums.HospitalType
import com.ark.sanjeevani.domain.repository.DatabaseRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HospitalViewModel(private val databaseRepo: DatabaseRepo) : ViewModel() {
    val logger = Logger.withTag("HospitalViewModel")

    private val _uiState = MutableStateFlow(HospitalUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HospitalUiEvent) {
        when (event) {
            HospitalUiEvent.ClearError -> _uiState.update { it.copy(errorMsg = null) }
            is HospitalUiEvent.GetHospitals -> getHospitals(event.type)
            is HospitalUiEvent.GetHospitalsRoms -> getHospitalRooms(event.id)
            is HospitalUiEvent.GetHospitalById -> getHospitalById(event.id)
        }
    }

    private fun getHospitalById(hospitalId: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                databaseRepo.getHospitalById(hospitalId).onSuccess { hospital ->
                    _uiState.update { it.copy(hospital = hospital, isLoading = false) }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            errorMsg = error.message ?: "Failed to fetch hospital details.",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                logger.e(e) { "Error fetching hospital details: ${e.message}" }
                _uiState.update {
                    it.copy(
                        errorMsg = "Something went wrong while fetching hospital details.",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getHospitalRooms(hospitalId: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                databaseRepo.getHospitalRooms(hospitalId).onSuccess { rooms ->
                    _uiState.update { it.copy(rooms = rooms, isLoading = false) }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            errorMsg = error.message ?: "Failed to fetch hospital rooms.",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                logger.e(e) { "Error fetching hospital rooms: ${e.message}" }
                _uiState.update {
                    it.copy(
                        errorMsg = "Something went wrong while fetching hospital rooms.",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getHospitals(type: HospitalType) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                databaseRepo.getAllHospitals(
                    lat = "28.6139", // Example latitude
                    lon = "77.2090", // Example longitude
                    type = type
                ).onSuccess { hospitals ->
                    _uiState.update { it.copy(allHospitals = hospitals, isLoading = false) }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            errorMsg = error.message ?: "Failed to fetch hospitals.",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                logger.e(e) { "Error fetching hospitals: ${e.message}" }
                _uiState.update {
                    it.copy(
                        errorMsg = "Something went wrong while fetching hospitals.",
                        isLoading = false
                    )
                }
            }
        }
    }

}