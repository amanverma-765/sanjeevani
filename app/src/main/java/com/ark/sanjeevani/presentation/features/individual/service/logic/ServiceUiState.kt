package com.ark.sanjeevani.presentation.features.individual.service.logic

import com.ark.sanjeevani.domain.model.Dietitian
import com.ark.sanjeevani.domain.model.Doctor
import com.ark.sanjeevani.domain.model.DoctorCategory
import com.ark.sanjeevani.domain.model.Physiotherapist

data class ServiceUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val doctors: List<Doctor> = emptyList(),
    val physiotherapists: List<Physiotherapist> = emptyList(),
    val dietitians: List<Dietitian> = emptyList(),
    val doctorCategories: List<DoctorCategory> = emptyList()
)
