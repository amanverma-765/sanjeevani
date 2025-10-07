package com.ark.sanjeevani.presentation.features.auth.logic.reg

import com.ark.sanjeevani.domain.enums.Gender
import com.ark.sanjeevani.domain.enums.LoginRole
import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.ark.sanjeevani.domain.model.RegisteredUser

data class RegistrationUiState(
    val isLoading: Boolean = false,
    val errorMsg: String? = null,

    // Auth info
    val userInfo: LoginUserInfo? = null,
    val authErrorMsg: String? = null,

    // Form data
    val states: List<String> = emptyList(),
    val cities: List<String> = emptyList(),

    // User data
    val registeredUser: RegisteredUser? = null,
    val registrationError: String? = null,
    val isRegistrationComplete: Boolean = false,

    // Form fields
    val selectedRole: LoginRole? = null,
    val name: String = "",
    val dateOfBirth: String = "",
    val selectedGender: Gender? = null,
    val mobileNumber: String = "",
    val selectedState: String = "",
    val selectedCity: String = "",
    val acceptTerms: Boolean = false,

    // Validation errors
    val nameError: String? = null,
    val dateOfBirthError: String? = null,
    val genderError: String? = null,
    val mobileNumberError: String? = null,
    val stateError: String? = null,
    val cityError: String? = null,
    val roleError: String? = null,
    val termsError: String? = null,

    // Form state
    val isFormValid: Boolean = false,
    val hasAttemptedSubmit: Boolean = false
)
