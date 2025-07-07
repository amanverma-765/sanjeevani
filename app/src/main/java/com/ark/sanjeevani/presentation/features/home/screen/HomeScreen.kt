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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ark.sanjeevani.presentation.features.home.components.HomeTopBar
import com.ark.sanjeevani.presentation.features.home.logic.HomeUiEvent
import com.ark.sanjeevani.presentation.features.home.logic.HomeViewModel
import com.ark.sanjeevani.utils.toastShort
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onNotificationClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    uiState.errorMsg?.let { errorMsg ->
        LaunchedEffect(errorMsg) {
            context.toastShort(errorMsg)
            viewModel.onEvent(HomeUiEvent.ClearErrorMsg)
        }
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                userName = uiState.userInfo?.name?.split(" ")?.firstOrNull() ?: "User",
                userProfileUrl = uiState.userInfo?.profileUrl ?: "",
                onNotificationClicked = onNotificationClicked,
                onProfileClicked = {}
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