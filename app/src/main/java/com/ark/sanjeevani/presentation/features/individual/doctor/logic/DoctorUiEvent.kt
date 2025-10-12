package com.ark.sanjeevani.presentation.features.individual.doctor.logic

sealed interface DoctorUiEvent {
    data object ClearError : DoctorUiEvent
    data object GetDoctorCategories : DoctorUiEvent
    data class GetDoctorList(val categoryId: String) : DoctorUiEvent
}