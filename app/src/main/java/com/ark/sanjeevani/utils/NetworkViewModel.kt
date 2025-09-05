package com.ark.sanjeevani.utils


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NetworkViewModel(
    private val connectivity: Connectivity
) : ViewModel() {

    private val _networkState = MutableStateFlow(NetworkState())
    val networkState: StateFlow<NetworkState> = _networkState.asStateFlow()

    init {
        viewModelScope.launch {
            connectivity.statusUpdates.collect { status ->
                val wasConnected = _networkState.value.isConnected
                val isConnected = when (status) {
                    is Connectivity.Status.Connected -> true
                    is Connectivity.Status.Disconnected -> false
                }

                _networkState.value = _networkState.value.copy(
                    isConnected = isConnected,
                    reconnectionCount = if (!wasConnected && isConnected) {
                        _networkState.value.reconnectionCount + 1
                    } else {
                        _networkState.value.reconnectionCount
                    }
                )
            }
        }
    }
}

data class NetworkState(
    val isConnected: Boolean = true,
    val reconnectionCount: Int = 0
)