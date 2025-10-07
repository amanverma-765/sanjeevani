package com.ark.sanjeevani.presentation.features.auth.logic.reg

import com.ark.sanjeevani.domain.enums.Gender
import com.ark.sanjeevani.domain.enums.LoginRole

sealed interface RegistrationUiEvent {
    data object ClearErrorMsg : RegistrationUiEvent
    data object ClearValidationErrors : RegistrationUiEvent
    data object SubmitForm : RegistrationUiEvent
    data object ValidateForm : RegistrationUiEvent

    data class UpdateRole(val role: LoginRole) : RegistrationUiEvent
    data class UpdateName(val name: String) : RegistrationUiEvent
    data class UpdateDateOfBirth(val dateOfBirth: String) : RegistrationUiEvent
    data class UpdateGender(val gender: Gender) : RegistrationUiEvent
    data class UpdateMobileNumber(val mobileNumber: String) : RegistrationUiEvent
    data class UpdateState(val state: String) : RegistrationUiEvent
    data class UpdateCity(val city: String) : RegistrationUiEvent
    data class UpdateTermsAcceptance(val accepted: Boolean) : RegistrationUiEvent

    data class GetRegisteredUser(val email: String) : RegistrationUiEvent
}
