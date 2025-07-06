package com.ark.sanjeevani.presentation.features.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.ark.sanjeevani.presentation.features.home.components.HomeTopBar
import com.ark.sanjeevani.presentation.features.home.logic.HomeUiEvent
import com.ark.sanjeevani.presentation.features.home.logic.HomeViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onUserNotAuthenticated: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.onEvent(HomeUiEvent.GetAuthenticatedUser)
    }

    LaunchedEffect(
        key1 = uiState.userInfo,
        key2 = uiState.errorMsg,
        key3 = uiState.isLoading
    ) {
        Logger.e("${uiState.userInfo}")
        if (!uiState.isLoading && uiState.userInfo == null && uiState.errorMsg != null) {
            onUserNotAuthenticated()
        }
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                userName = uiState.userInfo
                    ?.userMetadata?.getValue("name")
                    .toString().replace("\"", "")
                    .split(" ")
                    .firstOrNull()
                    ?: "User",
                userProfileUrl = uiState.userInfo?.userMetadata?.getValue("avatar_url")
                    .toString().replace("\"", "")
            )
        }
    ) { innerPadding ->
        Box(
            modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}