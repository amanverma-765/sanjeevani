package com.ark.sanjeevani.presentation.features.home.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ark.sanjeevani.domain.repository.SupabaseRepo
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val supabaseRepo: SupabaseRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.ClearErrorMsg -> _uiState.update { it.copy(errorMsg = null) }
            HomeUiEvent.GetAuthenticatedUser -> getAuthenticatedUser()
        }
    }

    init {
        getAuthenticatedUser()
    }

    private fun getAuthenticatedUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                supabaseRepo.listenAuthStatus().collectLatest { apiResponse ->
                    apiResponse.onSuccess {
                        _uiState.update { it.copy(isLoading = false, userInfo = data) }
                    }.onFailure {
                        _uiState.update { it.copy(isLoading = false, errorMsg = message()) }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(errorMsg = "Something went wrong.", isLoading = false) }
            }
        }
    }
}