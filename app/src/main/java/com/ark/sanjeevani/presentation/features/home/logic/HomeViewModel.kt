package com.ark.sanjeevani.presentation.features.home.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.domain.model.mockBanners
import com.ark.sanjeevani.domain.model.mockServices
import com.ark.sanjeevani.domain.repository.AuthenticationRepo
import com.ark.sanjeevani.domain.repository.DatabaseRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authenticationRepo: AuthenticationRepo,
    private val databaseRepo: DatabaseRepo
) : ViewModel() {

    val logger = Logger.withTag("HomeViewModel")

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.ClearErrorMsg -> _uiState.update { it.copy(errorMsg = null) }
            is HomeUiEvent.GetRegisteredUser -> getRegisteredUser(event.email)
        }
    }

    init {
        listenAuthStatus()
        getBanners()
        getAvailableServices()
    }

    private fun getAvailableServices() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isServicesLoading = true) }
                // Service retrieval logic here
                val services = mockServices
                _uiState.update { it.copy(services = services, isServicesLoading = false) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(
                        errorMsg = "Something went wrong while fetching services.",
                        isServicesLoading = false
                    )
                }
            }
        }
    }

    private fun getBanners() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isBannerLoading = true) }
                // Banner retrieval logic here
                val banners = mockBanners
                _uiState.update { it.copy(banners = banners, isBannerLoading = false) }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(
                        errorMsg = "Something went wrong while fetching banners.",
                        isBannerLoading = false
                    )
                }
            }
        }
    }

    private fun getRegisteredUser(email: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(registrationError = null, registeredUser = null) }
                databaseRepo.getRegisteredUser(email).onSuccess { user ->
                    _uiState.update {
                        it.copy(registeredUser = user, registrationError = null)
                    }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(
                            registrationError = error.message ?: "Something went wrong, try again."
                        )
                    }
                }
            } catch (e: Exception) {
                logger.e(e) { "Error fetching registered user: ${e.message}" }
                _uiState.update {
                    it.copy(
                        registrationError = "Something went wrong, try again."
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
                                isUserLoading = false,
                                authError = null
                            )
                        },
                        onFailure = { error ->
                            state.copy(
                                isUserLoading = false,
                                authError = error.message
                            )
                        }
                    )
                }
            }
            .catch { e ->
                logger.e(e) { "Error fetching login status" }
                _uiState.update {
                    it.copy(isUserLoading = false, authError = "Something went wrong, try again.")
                }
            }
            .launchIn(viewModelScope)
    }
}