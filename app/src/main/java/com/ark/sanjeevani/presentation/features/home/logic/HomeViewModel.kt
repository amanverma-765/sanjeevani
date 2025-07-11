package com.ark.sanjeevani.presentation.features.home.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ark.sanjeevani.domain.model.mockBanners
import com.ark.sanjeevani.domain.model.mockServices
import com.ark.sanjeevani.domain.repository.SupabaseRepo
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.delay
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
                _uiState.update { it.copy(errorMsg = "Something went wrong while fetching services.", isServicesLoading = false) }
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
                _uiState.update { it.copy(errorMsg = "Something went wrong while fetching banners.", isBannerLoading = false) }
            }
        }
    }

    private fun getAuthenticatedUser() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isUserLoading = true) }
                supabaseRepo.listenAuthStatus().collectLatest { apiResponse ->
                    apiResponse.onSuccess {
                        _uiState.update { it.copy(isUserLoading = false, userInfo = data) }
                    }.onFailure {
                        _uiState.update { it.copy(isUserLoading = false, errorMsg = message()) }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(errorMsg = "Something went wrong while authenticating user.", isUserLoading = false) }
            }
        }
    }
}