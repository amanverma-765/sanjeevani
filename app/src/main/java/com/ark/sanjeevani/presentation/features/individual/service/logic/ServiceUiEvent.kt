package com.ark.sanjeevani.presentation.features.individual.service.logic

import com.ark.sanjeevani.presentation.features.individual.home.logic.ServiceType

sealed interface ServiceUiEvent {
    data object ClearError : ServiceUiEvent

    data class GetDoctorList(val categoryId: String) : ServiceUiEvent

    data class GetService(val type: ServiceType) : ServiceUiEvent
}