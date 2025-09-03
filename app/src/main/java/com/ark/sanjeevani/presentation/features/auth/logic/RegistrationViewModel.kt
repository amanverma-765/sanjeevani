package com.ark.sanjeevani.presentation.features.auth.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ark.sanjeevani.domain.enums.LoginRole
import com.ark.sanjeevani.domain.repository.SupabaseRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(private val supabaseRepo: SupabaseRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegistrationUiEvent) {
        when (event) {
            RegistrationUiEvent.ClearErrorMsg -> _uiState.update { it.copy(errorMsg = null) }
            RegistrationUiEvent.ClearValidationErrors -> clearValidationErrors()
            RegistrationUiEvent.SubmitForm -> submitForm()
            RegistrationUiEvent.ValidateForm -> validateForm()

            is RegistrationUiEvent.UpdateRole -> updateRole(event.role)
            is RegistrationUiEvent.UpdateName -> updateName(event.name)
            is RegistrationUiEvent.UpdateDateOfBirth -> updateDateOfBirth(event.dateOfBirth)
            is RegistrationUiEvent.UpdateGender -> updateGender(event.gender)
            is RegistrationUiEvent.UpdateMobileNumber -> updateMobileNumber(event.mobileNumber)
            is RegistrationUiEvent.UpdateState -> updateState(event.state)
            is RegistrationUiEvent.UpdateCity -> updateCity(event.city)
            is RegistrationUiEvent.UpdateTermsAcceptance -> updateTermsAcceptance(event.accepted)
        }
    }

    private fun updateRole(role: LoginRole) {
        _uiState.update {
            it.copy(
                selectedRole = role,
                roleError = if (it.hasAttemptedSubmit) RegistrationValidator.validateRole(role) else null
            )
        }
        validateFormIfAttempted()
    }

    private fun updateName(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                nameError = if (it.hasAttemptedSubmit) RegistrationValidator.validateName(name) else null
            )
        }
        validateFormIfAttempted()
    }

    private fun updateDateOfBirth(dateOfBirth: String) {
        _uiState.update {
            it.copy(
                dateOfBirth = dateOfBirth,
                dateOfBirthError = if (it.hasAttemptedSubmit) RegistrationValidator.validateDateOfBirth(dateOfBirth) else null
            )
        }
        validateFormIfAttempted()
    }

    private fun updateGender(gender: String) {
        _uiState.update {
            it.copy(
                selectedGender = gender,
                genderError = if (it.hasAttemptedSubmit) RegistrationValidator.validateGender(gender) else null
            )
        }
        validateFormIfAttempted()
    }

    private fun updateMobileNumber(mobileNumber: String) {
        // Filter to only digits and limit to 10 characters
        val filteredNumber = mobileNumber.filter { it.isDigit() }.take(10)
        _uiState.update {
            it.copy(
                mobileNumber = filteredNumber,
                mobileNumberError = if (it.hasAttemptedSubmit) RegistrationValidator.validateMobileNumber(filteredNumber) else null
            )
        }
        validateFormIfAttempted()
    }

    private fun updateState(state: String) {
        _uiState.update {
            it.copy(
                selectedState = state,
                selectedCity = "", // Reset city when state changes
                stateError = if (it.hasAttemptedSubmit) RegistrationValidator.validateState(state) else null,
                cityError = if (it.hasAttemptedSubmit) RegistrationValidator.validateCity("") else null
            )
        }
        validateFormIfAttempted()
    }

    private fun updateCity(city: String) {
        _uiState.update {
            it.copy(
                selectedCity = city,
                cityError = if (it.hasAttemptedSubmit) RegistrationValidator.validateCity(city) else null
            )
        }
        validateFormIfAttempted()
    }

    private fun updateTermsAcceptance(accepted: Boolean) {
        _uiState.update {
            it.copy(
                acceptTerms = accepted,
                termsError = if (it.hasAttemptedSubmit) RegistrationValidator.validateTermsAcceptance(accepted) else null
            )
        }
        validateFormIfAttempted()
    }

    private fun clearValidationErrors() {
        _uiState.update {
            it.copy(
                nameError = null,
                dateOfBirthError = null,
                genderError = null,
                mobileNumberError = null,
                stateError = null,
                cityError = null,
                roleError = null,
                termsError = null
            )
        }
    }

    private fun validateForm() {
        val currentState = _uiState.value

        val nameError = RegistrationValidator.validateName(currentState.name)
        val dateOfBirthError = RegistrationValidator.validateDateOfBirth(currentState.dateOfBirth)
        val genderError = RegistrationValidator.validateGender(currentState.selectedGender)
        val mobileNumberError = RegistrationValidator.validateMobileNumber(currentState.mobileNumber)
        val stateError = RegistrationValidator.validateState(currentState.selectedState)
        val cityError = RegistrationValidator.validateCity(currentState.selectedCity)
        val roleError = RegistrationValidator.validateRole(currentState.selectedRole)
        val termsError = RegistrationValidator.validateTermsAcceptance(currentState.acceptTerms)

        val isFormValid = listOf(
            nameError, dateOfBirthError, genderError, mobileNumberError,
            stateError, cityError, roleError, termsError
        ).all { it == null }

        _uiState.update {
            it.copy(
                nameError = nameError,
                dateOfBirthError = dateOfBirthError,
                genderError = genderError,
                mobileNumberError = mobileNumberError,
                stateError = stateError,
                cityError = cityError,
                roleError = roleError,
                termsError = termsError,
                isFormValid = isFormValid,
                hasAttemptedSubmit = true
            )
        }
    }

    private fun validateFormIfAttempted() {
        if (_uiState.value.hasAttemptedSubmit) {
            validateForm()
        }
    }

    private fun submitForm() {
        validateForm()

        if (_uiState.value.isFormValid) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }

                try {
                    // Here you would implement the actual registration logic
                    // using supabaseRepo or your preferred method

                    // For now, just simulate success
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMsg = null
                        )
                    }

                    // You can add a success callback or navigation logic here

                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMsg = e.message ?: "Registration failed. Please try again."
                        )
                    }
                }
            }
        }
    }
}
