package com.ark.sanjeevani.presentation.features.individual.doctor.logic

import com.ark.sanjeevani.domain.model.Doctor
import com.ark.sanjeevani.domain.model.DoctorCategory

data class DoctorUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val doctors: List<Doctor> = emptyList(),
    val doctorCategories: List<DoctorCategory> = emptyList()
)
