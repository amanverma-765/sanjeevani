package com.ark.sanjeevani.presentation.features.auth.logic.reg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.domain.enums.Gender
import com.ark.sanjeevani.domain.enums.LoginRole
import com.ark.sanjeevani.domain.model.RegisteredUser
import com.ark.sanjeevani.domain.repository.AuthenticationRepo
import com.ark.sanjeevani.domain.repository.DatabaseRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class RegistrationViewModel(
    private val authenticationRepo: AuthenticationRepo,
    private val databaseRepo: DatabaseRepo
) : ViewModel() {

    val logger = Logger.withTag("RegistrationViewModel")

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegistrationUiEvent) {
        when (event) {
            RegistrationUiEvent.ClearErrorMsg -> _uiState.update {
                it.copy(
                    errorMsg = null,
                    authErrorMsg = null
                )
            }

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
            is RegistrationUiEvent.GetRegisteredUser -> getRegisteredUser(event.email)
        }
    }

    init {
        listenAuthStatus()
        fetchCities()
        fetchStates()
    }

    @OptIn(ExperimentalTime::class)
    private fun registerNewUser() {
        viewModelScope.launch {
            try {
                val user = RegisteredUser(
                    email = uiState.value.userInfo?.email!!,
                    name = uiState.value.name,
                    createdAt = Clock.System.now().toString(),
                    dob = uiState.value.dateOfBirth,
                    phone = uiState.value.mobileNumber,
                    avatar = uiState.value.userInfo?.profileUrl,
                    countryCode = "+91",
                    role = uiState.value.selectedRole!!,
                    gender = uiState.value.selectedGender!!,
                    state = uiState.value.selectedState,
                    city = uiState.value.selectedCity,
                )

                authenticationRepo.registerNewUser(user).onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registrationError = null,
                            isRegistrationComplete = true // Add this flag
                        )
                    }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registrationError = error.message ?: "Something went wrong, try again."
                        )
                    }
                }
            } catch (e: Exception) {
                logger.e(e) { "Error registering new user: ${e.message}" }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        registrationError = "Something went wrong, try again."
                    )
                }
            }
        }
    }

    private fun getRegisteredUser(email: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, registrationError = null) }
                authenticationRepo.getRegisteredUser(email).onSuccess { user ->
                    _uiState.update {
                        it.copy(
                            registeredUser = user,
                            isLoading = false,
                            registrationError = null,
                            isRegistrationComplete = user != null // User exists, registration complete
                        )
                    }
                }.onFailure { error ->
                    // User not found - this is expected for new users
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registrationError = null, // Don't show error for user not found
                            registeredUser = null
                        )
                    }
                }
            } catch (e: Exception) {
                logger.e(e) { "Error fetching registered user: ${e.message}" }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        registrationError = null, // Don't show error for user lookup
                        registeredUser = null
                    )
                }
            }
        }
    }

    private fun fetchCities() {
        viewModelScope.launch {
            try {
                databaseRepo.getAllCities().onSuccess { cities ->
                    _uiState.update {
                        it.copy(
                            cities = cities,
                            errorMsg = null
                        )
                    }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            errorMsg = error.message ?: "Failed to load cities."
                        )
                    }
                }
            } catch (e: Exception) {
                logger.e(e) { "Error fetching cities: ${e.message}" }
                _uiState.update {
                    it.copy(
                        errorMsg = "Failed to load cities."
                    )
                }
            }
        }
    }

    private fun fetchStates() {
        viewModelScope.launch {
            try {
                databaseRepo.getAllStates().onSuccess { states ->
                    _uiState.update {
                        it.copy(
                            states = states,
                            errorMsg = null
                        )
                    }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            errorMsg = error.message ?: "Failed to load states."
                        )
                    }
                }
            } catch (e: Exception) {
                logger.e(e) { "Error fetching states: ${e.message}" }
                _uiState.update {
                    it.copy(
                        errorMsg = "Failed to load states."
                    )
                }
            }
        }
    }

    private fun listenAuthStatus() {
        authenticationRepo.authState
            .onEach { result ->
                _uiState.update { state ->
                    result.fold(
                        onSuccess = { userInfo ->
                            state.copy(
                                userInfo = userInfo,
                                isLoading = false,
                                authErrorMsg = null
                            )
                        },
                        onFailure = { error ->
                            state.copy(
                                isLoading = false,
                                authErrorMsg = error.message
                            )
                        }
                    )
                }
            }
            .catch { e ->
                logger.e(e) { "Error fetching login status" }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        authErrorMsg = "Authentication error. Please login again."
                    )
                }
            }
            .launchIn(viewModelScope)
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
                dateOfBirthError = if (it.hasAttemptedSubmit) RegistrationValidator.validateDateOfBirth(
                    dateOfBirth
                ) else null
            )
        }
        validateFormIfAttempted()
    }

    private fun updateGender(gender: Gender) {
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
                mobileNumberError = if (it.hasAttemptedSubmit) RegistrationValidator.validateMobileNumber(
                    filteredNumber
                ) else null
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
                termsError = if (it.hasAttemptedSubmit) RegistrationValidator.validateTermsAcceptance(
                    accepted
                ) else null
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
        val mobileNumberError =
            RegistrationValidator.validateMobileNumber(currentState.mobileNumber)
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
            registerNewUser()
        }
    }
}