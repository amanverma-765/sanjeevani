package com.ark.sanjeevani.presentation.features.tabs.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.domain.repository.AuthenticationRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TabViewModel(
    private val authenticationRepo: AuthenticationRepo,
    initialTab: TabDestinations? = null
) : ViewModel() {

    val logger = Logger.withTag("TabViewModel")

    private val _uiState = MutableStateFlow(
        TabUiState(selectedDestination = initialTab ?: TabDestinations.Home)
    )
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: TabUiEvent) {
        when (event) {
            TabUiEvent.ClearError -> _uiState.update {
                it.copy(
                    registrationError = null,
                    authError = null
                )
            }

            is TabUiEvent.SelectDestination -> {
                _uiState.update {
                    it.copy(selectedDestination = event.destination)
                }
            }
            is TabUiEvent.GetRegisteredUser -> getRegisteredUser(event.email)
        }
    }

    init {
        listenAuthStatus()
    }

    private fun getRegisteredUser(email: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(registrationError = null, registeredUser = null) }
                authenticationRepo.getRegisteredUser(email).onSuccess { user ->
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