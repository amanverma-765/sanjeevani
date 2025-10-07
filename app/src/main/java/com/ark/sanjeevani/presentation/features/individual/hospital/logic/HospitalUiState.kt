package com.ark.sanjeevani.presentation.features.individual.hospital.logic

import com.ark.sanjeevani.domain.model.Hospital
import com.ark.sanjeevani.domain.model.HospitalRoom

data class HospitalUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val allHospitals: List<Hospital> = emptyList(),
    val hospital: Hospital? = null,
    val rooms: List<HospitalRoom> = emptyList()
)
