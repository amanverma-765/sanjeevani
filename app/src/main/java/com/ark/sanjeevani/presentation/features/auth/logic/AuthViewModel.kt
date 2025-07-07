package com.ark.sanjeevani.presentation.features.auth.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.domain.model.LoginUserInfo
import com.ark.sanjeevani.domain.repository.SupabaseRepo
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthViewModel(private val supabaseRepo: SupabaseRepo) : ViewModel() {

    private val _userInfo = MutableStateFlow<LoginUserInfo?>(null)
    val userInfo = _userInfo.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        listenAuthStatus()
    }

    private fun listenAuthStatus() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                supabaseRepo.listenAuthStatus().collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _userInfo.value = response.data
                            _isLoading.value = false
                        }
                        else -> {
                            _userInfo.value = null
                            _isLoading.value = false
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _userInfo.value = null
                _isLoading.value = false
            }
        }
    }

}