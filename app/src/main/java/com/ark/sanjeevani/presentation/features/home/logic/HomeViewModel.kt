package com.ark.sanjeevani.presentation.features.home.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.domain.SupabaseRepo
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val supabaseRepo: SupabaseRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.GetAuthenticatedUser -> getAuthenticatedUser()
        }
    }

    private fun getAuthenticatedUser() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        try {
            when (val response = supabaseRepo.getAuthenticatedUser()) {
                is ApiResponse.Success -> {
                    Logger.i("Logged in as: ${response.data.email}")
                    _uiState.update {
                        it.copy(
                            userInfo = response.data,
                            isLoading = false,
                            errorMsg = null
                        )
                    }
                }

                is ApiResponse.Failure.Error -> _uiState.update {
                    it.copy(
                        errorMsg = response.message(),
                        isLoading = false
                    )
                }

                is ApiResponse.Failure.Exception -> _uiState.update {
                    it.copy(
                        errorMsg = "Something went wrong.",
                        isLoading = false
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _uiState.update { it.copy(errorMsg = "Something went wrong.", isLoading = false) }
        }
    }
}