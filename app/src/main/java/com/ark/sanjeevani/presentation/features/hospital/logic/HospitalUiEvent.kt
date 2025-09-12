package com.ark.sanjeevani.presentation.features.hospital.logic

import com.ark.sanjeevani.domain.enums.HospitalType

sealed interface HospitalUiEvent {
    data class GetHospitals(val type: HospitalType) : HospitalUiEvent
    data class GetHospitalsRoms(val id: String) : HospitalUiEvent
    data class GetHospitalById(val id: String) : HospitalUiEvent
    data object ClearError : HospitalUiEvent
}