package com.ark.sanjeevani.presentation.features.individual.doctor.logic

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.domain.repository.DatabaseRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DoctorViewModel(private val databaseRepo: DatabaseRepo) : ViewModel() {
    val logger = Logger.withTag("HospitalViewModel")

    private val _uiState = MutableStateFlow(DoctorUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: DoctorUiEvent) {
        when (event) {
            DoctorUiEvent.ClearError -> _uiState.update { it.copy(errorMsg = null) }
        }
    }


}