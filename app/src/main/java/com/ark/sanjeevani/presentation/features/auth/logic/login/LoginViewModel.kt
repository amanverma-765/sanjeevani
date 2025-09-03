package com.ark.sanjeevani.presentation.features.auth.logic.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.domain.repository.AuthenticationRepo
import com.ark.sanjeevani.utils.initiateGoogleOAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val authenticationRepo: AuthenticationRepo) : ViewModel() {

    val logger = Logger.withTag("LoginViewModel")

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            LoginUiEvent.ClearErrorMsg -> _uiState.update { it.copy(errorMsg = null) }
            is LoginUiEvent.LoginWithFb -> TODO()
            is LoginUiEvent.LoginWithGoogle -> loginWithGoogle(event.context)
        }
    }

    private fun loginWithGoogle(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMsg = null) }
            initiateGoogleOAuth(context).fold(
                onSuccess = { oauthResult ->
                    try {
                        val loginResult = authenticationRepo.loginWithGoogle(
                            oauthResult.idToken,
                            oauthResult.hashedNonce
                        )
                        loginResult.onFailure { error ->
                            _uiState.update {
                                it.copy(isLoading = false, errorMsg = "Something went wrong, try again.")
                            }
                        }.onSuccess {
                            _uiState.update { it.copy(isLoading = false, errorMsg = null) }
                            logger.i { "Logged in Successfully" }
                        }
                    } catch (e: Exception) {
                        logger.e(e) { "Error during backend authentication" }
                        _uiState.update {
                            it.copy(isLoading = false, errorMsg = "Authentication failed, try again.")
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
