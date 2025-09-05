package com.ark.sanjeevani.presentation.features.auth.logic.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import coil3.util.CoilUtils.result
import com.ark.sanjeevani.domain.repository.AuthenticationRepo
import com.ark.sanjeevani.utils.initiateGoogleOAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val authenticationRepo: AuthenticationRepo) : ViewModel() {

    val logger = Logger.withTag("LoginViewModel")
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            LoginUiEvent.ClearErrorMsg -> _uiState.update { it.copy(errorMsg = null) }
            is LoginUiEvent.LoginWithFb -> loginWithFb()
            is LoginUiEvent.LoginWithGoogle -> loginWithGoogle(event.context)
        }
    }

    init {
        listenAuthStatus()
    }

    private fun listenAuthStatus() {
        authenticationRepo.authState
            .onEach { result ->
                _uiState.update { state ->
                    result.fold(
                        onSuccess = { userInfo ->
                            state.copy(
                                isLoading = false,
                                userState = userInfo,
                                errorMsg = null
                            )
                        },
                        onFailure = { error ->
                            state.copy(
                                isLoading = false,
                                errorMsg = error.message
                            )
                        }
                    )
                }
            }
            .catch { e ->
                logger.e(e) { "Error fetching login status" }
                _uiState.update {
                    it.copy(isLoading = false, errorMsg = "Something went wrong, try again.")
                }
            }
            .launchIn(viewModelScope)
    }


    private fun loginWithGoogle(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMsg = null) }
            initiateGoogleOAuth(context).fold(
                onSuccess = { oauthResult ->
                    try {
                        val loginResult = authenticationRepo.loginWithGoogle(oauthResult.idToken)
                        loginResult.onFailure { error ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMsg = "Something went wrong, try again."
                                )
                            }
                        }.onSuccess {
                            _uiState.update { it.copy(isLoading = false, errorMsg = null) }
                            logger.i { "Logged in Successfully" }
                        }
                    } catch (e: Exception) {
                        logger.e(e) { "Error during backend authentication" }
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMsg = "Authentication failed, try again."
                            )
                        }
                    }
                },
                onFailure = { error ->
                    logger.e(error) { "Google OAuth failed" }
                    _uiState.update {
                        it.copy(isLoading = false, errorMsg = error.message)
                    }
                }
            )
        }
    }

    private fun loginWithFb() {
        // Implementation for Facebook login
    }
}
