package com.ark.sanjeevani.presentation.features.hospital.logic

import com.ark.sanjeevani.domain.model.Hospital

data class HospitalUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val hospitals: List<Hospital> = emptyList()
)
